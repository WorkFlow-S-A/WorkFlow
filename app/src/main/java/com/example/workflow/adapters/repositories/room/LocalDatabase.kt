package com.example.workflow.adapters.repositories.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [RoomEmployee::class, RoomTask::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeRoomDao
    abstract fun taskDao(): TaskRoomDao

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "local-database"
                ).build()
                .also { instance = it }
            }
        }
    }
}