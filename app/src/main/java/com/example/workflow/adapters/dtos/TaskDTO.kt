package com.example.workflow.adapters.dtos

import android.util.Log
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import java.text.SimpleDateFormat
import java.util.UUID

class TaskDTO {
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    lateinit var startHour: String
    lateinit var endHour: String
    var done: Boolean = false

    companion object{
        fun fromTask(task : Task) : TaskDTO{
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            val taskDTO : TaskDTO = TaskDTO()

            taskDTO.id = task.id.toString()
            taskDTO.name = task.name.name
            taskDTO.description = task.description.description
            taskDTO.startHour = format.format(task.startHour.startHour)
            taskDTO.endHour = format.format(task.endHour.endHour)
            taskDTO.done = task.done

            return taskDTO
        }

        fun toTask(taskDTO: TaskDTO) : Task{
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            try{
                return Task(
                    id = UUID.fromString(taskDTO.id),
                    name = TaskName(taskDTO.name),
                    description = TaskDescription(taskDTO.description),
                    startHour = StartHour(format.parse(taskDTO.startHour)!!),
                    endHour = EndHour(format.parse(taskDTO.endHour)!!),
                    done = taskDTO.done
                )
            }catch (e : Exception){
                Log.e("TaskDTO ERROR", e.cause?.toString() ?:"Converter error")
                throw e
            }


        }
    }
}