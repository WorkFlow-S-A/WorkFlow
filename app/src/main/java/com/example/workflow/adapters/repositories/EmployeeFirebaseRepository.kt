package com.example.workflow.adapters.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class EmployeeFirebaseRepository() : EmployeeRemoteRepository{
    private val db = FirebaseFirestore.getInstance().collection("users")

    override suspend fun insertEmployee(employee: Employee) {
        db.document(employee.id.toString()).set(employee)
    }

    override suspend fun getAllEmployeesStream(): Flow<List<Employee>> {
        TODO()
    }

    override suspend fun getByIdStream(id: UUID): Flow<Employee?> = flow{
        val data : DocumentSnapshot? =  db.document(id.toString()).get().await()
        if(data != null){
            emit(data.toObject(Employee::class.java))
        }
        emit(null)
    }

    override suspend fun deleteEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }
}