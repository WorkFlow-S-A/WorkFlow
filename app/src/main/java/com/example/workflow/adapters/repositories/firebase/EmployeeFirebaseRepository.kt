package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.adapters.dtos.EmployeeDTO
import com.example.workflow.domain.entities.AttendanceRecord
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlinx.coroutines.flow.flow

class EmployeeFirebaseRepository : EmployeeRemoteRepository{
    private val db = FirebaseFirestore.getInstance().collection("Company")


    override suspend fun insertEmployee(employee: Employee) {

        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Employees")
            .document(employee.id).set(EmployeeDTO.fromEmployee(employee))
            .addOnSuccessListener {
            Log.d("Firebase Operation", "successful insertion")
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase operation", exception.toString())
            }

    }


    override suspend fun getAllEmployeesStream(): Flow<List<Employee>> = flow {
        val querySnapshot = db.document(CompanyFirebaseRepository.getCurrentCompanyId())
            .collection("Employees").get().await()
        val dtoList : List<EmployeeDTO> = querySnapshot.toObjects(EmployeeDTO::class.java)

        emit(dtoList.map {
            EmployeeDTO.toEmployee(it)
        })

    }

    override suspend fun getByIdStream(id: String): Flow<Employee?> = flow{
        val data : DocumentSnapshot? =  db.document(CompanyFirebaseRepository.getCurrentCompanyId())
            .collection("Employees").document(id).get().await()
        if(data != null){
            val employeeDTO : EmployeeDTO? = data.toObject(EmployeeDTO :: class.java)
            if(employeeDTO == null){
                throw Error("Not found")
            } else {
                emit(EmployeeDTO.toEmployee(employeeDTO))
            }

        }else{
            emit(null)
        }
    }

    override suspend fun deleteEmployee(employee: Employee) {
        db.document(CompanyFirebaseRepository.getCurrentCompanyId())
            .collection("Employees").document(employee.id)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase operation", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e ->
                Log.w("Firebase operation", "Error deleting document", e) }
    }

    override suspend fun updateEmployeeField(id: String, newData : String, fieldType : String) {
        val data : DocumentSnapshot? =  db.document(CompanyFirebaseRepository.getCurrentCompanyId())
            .collection("Employees").document(id).get().await()

        if(data != null && data.exists()){
            if(data.data!!.contains(fieldType)){
                db.document(CompanyFirebaseRepository.getCurrentCompanyId())
                    .collection("Employees").document(id)
                    .update(fieldType, newData)
                    .addOnSuccessListener { Log.d("Firebase operation", "Document successfully updated!") }
                    .addOnFailureListener { e -> Log.w("Firebase operation", "Error updating document", e) }
            }else{
                Log.w("Firebase operation", "The field does not exist in this document")
            }
        }else{
            Log.w("Firebase operation", "Error updating document")
        }

    }

    suspend fun updateAttendanceHistoryList(id: String, newAttendanceHistory: List<AttendanceRecord>) {
        try {
            val document = db.document(CompanyFirebaseRepository.getCurrentCompanyId())
                .collection("Employees")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                val employeeRef = db.document(CompanyFirebaseRepository.getCurrentCompanyId())
                    .collection("Employees")
                    .document(id)

                val newData = hashMapOf<String, Any>("attendanceHistory" to newAttendanceHistory)

                employeeRef.update(newData)
                    .addOnSuccessListener {
                        Log.d("Firebase operation", "Document successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase operation", "Error updating document", e)
                    }
            } else {
                Log.w("Firebase operation", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("Firebase operation", "Error updating document: ${e.message}", e)
        }
    }

    override suspend fun checkIn(employee: Employee, checkInTime: String) {
        val currentDayAttendance = AttendanceRecord(checkInTime, "")
        employee.attendanceHistory.add(currentDayAttendance)
        if (employee.attendanceHistory.size > 31) {
            employee.attendanceHistory = employee.attendanceHistory.subList(employee.attendanceHistory.size - 31, employee.attendanceHistory.size).toMutableList()
        }
        updateAttendanceHistoryList(employee.id, employee.attendanceHistory.toList())
    }

    override suspend fun checkOut(employee: Employee, checkOutTime: String) {
        val lastRecord = employee.attendanceHistory.lastOrNull()
        if (lastRecord != null && lastRecord.checkOutTime.isBlank()) {
            lastRecord.checkOutTime = checkOutTime
        }
        updateAttendanceHistoryList(employee.id, employee.attendanceHistory.toList())
    }
}