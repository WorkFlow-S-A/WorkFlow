package com.example.workflow.domain.entities

import android.app.ActivityManager.TaskDescription
import java.util.Calendar
import java.util.UUID

data class Task(val id: UUID = UUID.randomUUID(),
                val name: TaskName,
                val description: TaskDescription,
                val startHour: StartHour,
                val endHour: EndHour,
                val done: Done) {
    fun getId(): UUID = id
    fun getName(): TaskName = name
    fun getDescription(): TaskDescription = description
    fun getStartHour(): StartHour = startHour
    fun getEndHour(): EndHour = endHour
    fun getDone(): Done = done
    override fun toString(): String {
        return "Task { ID = $id," +
                "name = $name," +
                "startHour = $startHour," +
                "endHour = $endHour," +
                "done: $done" +
                "}"
    }
}

@JvmInline
value class TaskName(private val name: String) {

    init {
        require(name != null) { "The value of name must not be null" }
        require(
            Regex("^[a-zA-Z]{2,}\$").matches(name)) {
            "The name must have at least two letters and consist only of letters."
        }
    }
    fun getName(): String = name
    override fun toString(): String {
        return "TaskName = $name"
    }
}

@JvmInline
value class TaskDescription(private val description: String) {
    init {
        require(description != null) { "The value of description must not be null" }
    }
    fun getDescription(): String = description
    override fun toString(): String {
        return "TaskDescription = $description"
    }

}

@JvmInline
value class StartHour(private val startHour: Calendar) {

    init {
        require(startHour != null) { "The value of startHour must not be null" }
        require(!startHour.before(Calendar.getInstance())) { "startHour must not be earlier than the current date." }
    }
    fun getStartHour(): Calendar = startHour
    override fun toString(): String {
        return "Start hour = $startHour"
    }
}

@JvmInline
value class EndHour(private val endHour: Calendar) {
    init {
        require(endHour != null) { "The value of endHour must not be null." }
        require(!endHour.before(Calendar.getInstance())) { "endHour must not be earlier than the current date." }
    }

    fun getEndHour(): Calendar = endHour
    override fun toString(): String {
        return "End hour = $endHour"
    }
}

@JvmInline
value class Done(private val done: Boolean) {

    init {
        require(done != null) { "The value of done must not be null." }
    }
    fun getDone(): Boolean = done
    override fun toString(): String {
        return "The task is " + if (done) "done" else "not done"
    }
}
