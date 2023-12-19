package com.example.workflow.adapters.repositories.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: RoomEmployee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<RoomEmployee>)

    @Update
    suspend fun update(employee: RoomEmployee)

    @Delete
    suspend fun delete(employee: RoomEmployee)

    @Query("SELECT * FROM employees WHERE id = :id")
    fun getEmployee(id: String): Flow<RoomEmployee>

    @Query("SELECT * FROM employees WHERE needSync = 1")
    fun getAllDesynchronizedEmployees(): List<RoomEmployee>

    @Query("DELETE FROM employees")
    suspend fun deleteAll()
}