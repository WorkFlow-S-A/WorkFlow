package com.example.workflow.ports.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.workflow.App
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.TaskLocalRepository
import com.example.workflow.ports.repository.TaskRemoteRepository
import com.example.workflow.utils.InternetChecker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class TaskService(): Service() {

    private val internetChecker = App.instance.internetChecker

    companion object{
        private lateinit var instance : TaskService
        lateinit var remoteRepository: TaskRemoteRepository
        lateinit var localRepository: TaskLocalRepository
        fun getService(taskRemoteRepository: TaskRemoteRepository,
                       taskLocalRepository: TaskLocalRepository, ) : TaskService {

            remoteRepository = taskRemoteRepository
            localRepository = taskLocalRepository
            instance = TaskService()
            return instance
        }
    }

    suspend fun getTaskById(id: UUID): Task {
        var taskStream: Flow<Task?> = localRepository.getTaskByIdStream(id = id)
        val task = taskStream.firstOrNull()
        if (task != null) {
            return task
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
        return if (internetChecker.checkConnectivity()) {
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
        if (internetChecker.checkConnectivity()) {
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
        if (internetChecker.checkConnectivity()) {
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
        if (internetChecker.checkConnectivity()) {
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