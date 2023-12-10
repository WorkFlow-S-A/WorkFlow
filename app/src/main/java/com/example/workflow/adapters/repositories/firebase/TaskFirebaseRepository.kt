package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.adapters.dtos.TaskDTO
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskRemoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TaskFirebaseRepository(): TaskRemoteRepository {

    val db = FirebaseFirestore.getInstance().collection("Company")

    override fun getTaskByIdStream(id: UUID): Flow<Task?> {
        TODO("Not yet implemented")
    }

    override fun getAllTasksStream(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(tasks: List<Task>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTask(task: Task) {
        db.document(CompanyFirebaseRepository.getCurrentCompanyId()).collection("Tasks")
            .document(task.id.toString()).set(TaskDTO.fromTask(task))
            .addOnSuccessListener {
                Log.d("Firebase Operation", "Task added success")
            }
            .addOnFailureListener {
                Log.w("Firebase Operation", it.message?: "Task did not add")
            }
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }
}