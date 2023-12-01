package com.example.workflow.adapters.repositories.room

import com.example.workflow.domain.entities.*
import com.example.workflow.ports.repository.TaskLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class TaskRoomRepository(private val taskDao: TaskRoomDao): TaskLocalRepository {
    override fun getTaskByIdStream(id: UUID): Flow<Task?> {
        val task = taskDao.getTask(id.toString())
        return task.map { roomTask ->
            roomTask.toTask()
        }
    }

    override fun getAllTasksStream(): Flow<List<Task>> {
        return taskDao.getAllTasks().flatMapLatest { roomTasks ->
            val tasks = roomTasks.map { it.toTask() }
            flowOf(tasks)
        } ?: flowOf(emptyList())
    }

    override fun getAllDesynchronizedTasks(): List<Task> {
        val roomDesynchronizedTasks = taskDao.getAllDesynchronizedTasks()
        return roomDesynchronizedTasks.map { it.toTask() }
    }

    override suspend fun saveAll(tasks: List<Task>) {
        val roomTasks = tasks.map { it.toTaskRoomEntity(false) }
        taskDao.insertAll(roomTasks)
    }

    override suspend fun insertTask(task: Task, needSync: Boolean) {
        taskDao.insert(task.toTaskRoomEntity(needSync))
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toTaskRoomEntity(false))
    }

    override suspend fun updateTask(task: Task, needSync: Boolean) {
        taskDao.update(task.toTaskRoomEntity(needSync))
    }

    private fun RoomTask.toTask(): Task {
        val startHourDate = Date(startHour)
        val endHourDate = Date(endHour)

        return Task(
            id = this.id,
            name = TaskName(this.name),
            description = TaskDescription(this.description),
            startHour = StartHour(startHourDate),
            endHour = EndHour(endHourDate),
            done = this.done
        )
    }

    private fun Task.toTaskRoomEntity(needSync: Boolean): RoomTask {
        return RoomTask(
            id = this.id,
            name = this.name.toString(),
            description = this.description.toString(),
            startHour = this.startHour.startHour.time,
            endHour = this.endHour.endHour.time,
            done = this.done,
            needSync = needSync
        )
    }
}