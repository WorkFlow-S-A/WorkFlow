package com.example.workflow.adapters.repositories.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class RoomTask(
    @PrimaryKey(autoGenerate = true)
    val id: UUID,
    val name: String,
    val description: String,
    val startHour: Long,
    val endHour: Long,
    val done: Boolean,
    val needSync: Boolean
)
