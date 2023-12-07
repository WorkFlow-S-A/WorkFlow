package com.example.workflow.adapters.repositories.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workflow.domain.entities.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.TreeSet
import java.util.UUID

@Entity(tableName = "employees")
data class RoomEmployee(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val employeeId: String,
    val name: String,
    val surname: String,
    val schedule: String,
    val workHours: Int,
    val workedHours: Int,
    val email: String,
    val needSync: Boolean
) {
    companion object {
        fun convertScheduleToJson(schedule: TreeSet<Task>): String {
            val gson = Gson()
            val type: Type = object: TypeToken<TreeSet<Task>>() {}.type
            return gson.toJson(schedule, type)
        }

        fun convertJsonToSchedule(scheduleJson: String): TreeSet<Task> {
            val gson = Gson()
            val type: Type = object: TypeToken<TreeSet<Task>>() {}.type
            return gson.fromJson(scheduleJson, type)
        }
    }
}