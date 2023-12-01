package com.example.workflow.adapters.dtos

import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSchedule
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EmployeeWorkedHours
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskComparator
import java.util.TreeSet
import java.util.UUID

class EmployeeDTO private constructor() {
    lateinit var id: String
    lateinit var employeeId: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var schedule: List<Task>
    var workHours: Int = 0
    var workedHours: Int = 0
    lateinit var email : String

    companion object{
        fun toEmployee(employeeDTO: EmployeeDTO) : Employee{
            val employee : Employee = Employee(
                id = UUID.fromString(employeeDTO.id),
                employeeId = EmployeeID(employeeDTO.employeeId),
                name = EmployeeName(employeeDTO.name),
                surname = EmployeeSurname(employeeDTO.surname),
                workHours = EmployeeWorkHours(employeeDTO.workHours),
                workedHours = EmployeeWorkedHours(employeeDTO.workedHours),
                email = Email(employeeDTO.email)
            )

            employee.schedule.schedule.addAll(employeeDTO.schedule)
            return employee
        }

        fun fromEmployee(employee: Employee) : EmployeeDTO{
            var employeeDTO = EmployeeDTO()
            employeeDTO.id = employee.id.toString()
            employeeDTO.name = employee.name.name
            employeeDTO.surname = employee.surname.surname
            employeeDTO.schedule = employee.schedule.schedule.toList()
            employeeDTO.workHours = employee.workHours.workHours
            employeeDTO.workedHours = employee.workedHours.workedHours
            employeeDTO.email = employee.email.email

            return employeeDTO
        }
    }
}