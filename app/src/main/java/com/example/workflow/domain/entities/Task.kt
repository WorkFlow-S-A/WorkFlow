package com.example.workflow.domain.entities

import java.util.Date
import java.util.UUID

data class Task(val id: UUID = UUID.randomUUID(),
                var name: TaskName,
                var description: TaskDescription,
                var startHour: StartHour,
                var endHour: EndHour,
                var done: Boolean) {
    override fun toString(): String {
        return "Task { ID = $id," +
                "name = $name," +
                "description = $description," +
                "startHour = $startHour," +
                "endHour = $endHour," +
                "done: $done" +
                "}"
    }
}

@JvmInline
value class TaskName(val name: String) {

    init {
        require(
            Regex("^[a-zA-Z]{2,}\$").matches(name)) {
            "The name must have at least two letters and consist only of letters."
        }
    }
    override fun toString(): String {
        return "TaskName = $name"
    }
}

@JvmInline
value class TaskDescription(val description: String) {
    init {
        require(description.length >= 100) { "The description length must be 100 character or fewer" }
    }
    override fun toString(): String {
        return "TaskDescription = $description"
    }

}

@JvmInline
value class StartHour(val startHour: Date) {
    init {
        require(!startHour.before(Date())) { "startHour must not be earlier than the current date." }
    }

    override fun toString(): String {
        return "Start hour = $startHour"
    }
}

@JvmInline
value class EndHour(val endHour: Date) {
    init {
        require(!endHour.before(Date())) { "endHour must not be earlier than the current date." }
    }
    override fun toString(): String {
        return "End hour = $endHour"
    }
}
