package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.adapters.dtos.EmployeeDTO
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlinx.coroutines.flow.flow

class EmployeeFirebaseRepository : EmployeeRemoteRepository{
    private val db = FirebaseFirestore.getInstance().collection("users")

    override suspend fun insertEmployee(employee: Employee) {
        db.document(employee.id.toString()).set(EmployeeDTO.fromEmployee(employee))
            .addOnSuccessListener {
            Log.d("Firebase Operation", "successful insertion")
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase operation", exception.toString())
            }

    }

    override suspend fun getAllEmployeesStream(): Flow<List<Employee>> = flow {
        val querySnapshot = db.get().await()
        val employees : List<EmployeeDTO?> = querySnapshot.documents.map { document ->
            document.toObject(EmployeeDTO::class.java)
        }
        val resultEmployees : List<Employee> = employees.filterNotNull().map { employeeDTO -> EmployeeDTO.toEmployee(
            employeeDTO
        )  }

        emit(resultEmployees)
    }

    override suspend fun getByIdStream(id: UUID): Flow<Employee?> = flow{
        val data : DocumentSnapshot? =  db.document(id.toString()).get().await()
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
        db.document(employee.id.toString())
            .delete()
            .addOnSuccessListener { Log.d("Firebase operation", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("Firebase operation", "Error deleting document", e) }
    }

    override suspend fun updateEmployeeField(id: UUID, newData : String, fieldType : String) {
        val data : DocumentSnapshot? =  db.document(id.toString()).get().await()

        if(data != null && data.exists()){
            if(data.data!!.contains(fieldType)){
                db.document(id.toString())
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
}