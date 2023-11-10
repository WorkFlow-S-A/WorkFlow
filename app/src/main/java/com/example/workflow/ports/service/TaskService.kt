package com.example.workflow.ports.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TaskService(private val taskRepository: TaskRepository): Service() {

    fun getTaskByIdStream(id: UUID): Flow<Task?> {
        return taskRepository.getByIdStream(id)
    }

    fun getAllTasksStream(): Flow<List<Task>> {
        return taskRepository.getAllTasksStream()
    }

    suspend fun createTask(task: Task) {
        taskRepository.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskRepository.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }
}