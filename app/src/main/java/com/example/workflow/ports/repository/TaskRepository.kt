package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TaskRepository {
    fun getByIdStream(id: UUID): Flow<Task?>
    fun getAllTasksStream(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
}