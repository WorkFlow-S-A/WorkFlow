package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TaskLocalRepository {
    fun getTaskByIdStream(id: UUID): Flow<Task?>
    fun getAllTasksStream(): Flow<List<Task>>
    fun getAllDesynchronizedTasks(): List<Task>
    suspend fun saveAll(tasks: List<Task>)
    suspend fun insertTask(task: Task, needSync: Boolean)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task, needSync: Boolean)
}