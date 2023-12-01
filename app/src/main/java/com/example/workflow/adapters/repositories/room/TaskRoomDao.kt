package com.example.workflow.adapters.repositories.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: RoomTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<RoomTask>)

    @Update
    suspend fun update(task: RoomTask)

    @Delete
    suspend fun delete(task: RoomTask)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: String): Flow<RoomTask>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<RoomTask>>

    @Query("SELECT * FROM tasks WHERE needSync = 1")
    fun getAllDesynchronizedTasks(): List<RoomTask>
}