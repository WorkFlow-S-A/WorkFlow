package com.example.workflow.adapters.repositories.firebase

import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskRemoteRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TaskFirebaseRepository(): TaskRemoteRepository {
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
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }
}