package com.example.workflow.ports.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskLocalRepository
import com.example.workflow.ports.repository.TaskRemoteRepository
import com.example.workflow.utils.InternetChecker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import java.lang.IllegalArgumentException
import java.util.UUID

class TaskService(private val localRepository: TaskLocalRepository,
                  private val remoteRepository: TaskRemoteRepository
): Service() {

    suspend fun getTaskById(id: UUID): Task {
        var taskStream: Flow<Task?> = localRepository.getTaskByIdStream(id = id)
        if (taskStream.count() > 0) {
            return taskStream.first()?: throw IllegalArgumentException("Not found")
        }

        taskStream = remoteRepository.getTaskByIdStream(id = id)
        if (taskStream.count() > 0) {
            val task: Task? = taskStream.first()
            if (task != null) {
                localRepository.insertTask(task, false)
                return task
            } else
                throw InternalError("There was a problem while the object was being found.")
        } else
            throw IllegalArgumentException("Not found")
    }

    suspend fun getAllTasks(): List<Task> {
        return if (InternetChecker.getInstance(null).checkConnectivity()) {
            try {
                val tasksFromRemote = remoteRepository.getAllTasksStream().first()
                localRepository.saveAll(tasksFromRemote)
                tasksFromRemote
            } catch (e: Exception) {
                localRepository.getAllTasksStream().first() // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            localRepository.getAllTasksStream().first()
        }
    }
    suspend fun createTask(task: Task) {
        if (InternetChecker.getInstance(null).checkConnectivity()) {
            try {
                remoteRepository.insertTask(task)
                localRepository.insertTask(task, false)
            } catch (e: Exception) {
                localRepository.insertTask(task, true) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            localRepository.insertTask(task, true)
        }
    }

    suspend fun deleteTask(task: Task) {
        if (InternetChecker.getInstance(null).checkConnectivity()) {
            try {
                remoteRepository.deleteTask(task)
                localRepository.deleteTask(task)
            } catch (e: Exception) {
                localRepository.deleteTask(task) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            localRepository.deleteTask(task)
        }
    }

    suspend fun updateTask(task: Task) {
        if (InternetChecker.getInstance(null).checkConnectivity()) {
            try {
                remoteRepository.updateTask(task)
                localRepository.updateTask(task, false)
            } catch (e: Exception) {
                localRepository.updateTask(task, true) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            localRepository.updateTask(task, true)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }
}