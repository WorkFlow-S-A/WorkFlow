package com.example.workflow.ports.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.workflow.App
import com.example.workflow.domain.entities.Task
import com.example.workflow.ports.repository.EmployeeLocalRepository
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import com.example.workflow.ports.repository.TaskLocalRepository
import com.example.workflow.ports.repository.TaskRemoteRepository
import com.example.workflow.utils.InternetChecker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.lang.IllegalArgumentException
import java.util.UUID

class TaskService: Service() {

    private val internetChecker : InternetChecker = App.instance.internetChecker

    companion object {

        private var instance : TaskService? = null
        var taskRemoteRepository: TaskRemoteRepository? = null
        var taskLocalRepository: TaskLocalRepository? = null
        private var thisContext : Context? = null
        fun getService(remoteRepository: TaskRemoteRepository? = null,
                       localRepository: TaskLocalRepository? = null,
                       context: Context? = null ) : TaskService{
            if(instance == null) {
                thisContext = context
                taskRemoteRepository = remoteRepository
                taskLocalRepository = localRepository
                return TaskService()
            }

            return instance!!

        }
    }

    suspend fun getTaskById(id: UUID): Task {
        var taskStream: Flow<Task?>? = taskLocalRepository?.getTaskByIdStream(id = id)
        var task = taskStream?.firstOrNull()
        if (task != null) {
            return task
        }

        taskStream = taskRemoteRepository?.getTaskByIdStream(id = id)
        task = taskStream?.firstOrNull()
        if (task != null) {
            taskLocalRepository?.insertTask(task, false)
            return task
        } else
            throw InternalError("There was a problem while the object was being found.")
    }

    suspend fun getAllTasks(): List<Task>? {
        return if (internetChecker.checkConnectivity()) {
            try {
                val tasksFromRemote = taskRemoteRepository?.getAllTasksStream()?.firstOrNull()
                if (tasksFromRemote != null) {
                    taskLocalRepository?.deleteAllTasks()
                    taskLocalRepository?.saveAll(tasksFromRemote)
                }
                tasksFromRemote
            } catch (e: Exception) {
                taskLocalRepository?.getAllTasksStream()?.first() // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            taskLocalRepository?.getAllTasksStream()?.firstOrNull()
        }
    }
    suspend fun createTask(task: Task) {
        if (internetChecker.checkConnectivity()) {
            try {
                taskRemoteRepository?.insertTask(task)
                taskLocalRepository?.insertTask(task, false)
            } catch (e: Exception) {
                taskLocalRepository?.insertTask(task, true) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            taskLocalRepository?.insertTask(task, true)
        }
    }

    suspend fun deleteTask(task: Task) {    // TODO: Realizar la lógica de sincronización para que cuando se elimine una tarea del local, al recuperar internet, también se elimine del remoto
        if (internetChecker.checkConnectivity()) {
            try {
                taskRemoteRepository?.deleteTask(task)
                taskLocalRepository?.deleteTask(task)
            } catch (e: Exception) {
                taskLocalRepository?.deleteTask(task) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            taskLocalRepository?.deleteTask(task)
        }
    }

    suspend fun updateTask(task: Task) {
        if (internetChecker.checkConnectivity()) {
            try {
                taskRemoteRepository?.updateTask(task)
                taskLocalRepository?.updateTask(task, false)
            } catch (e: Exception) {
                taskLocalRepository?.updateTask(task, true) // TODO: Cambiar por el error correspondiente de Firebase
            }
        } else {
            taskLocalRepository?.updateTask(task, true)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }
}