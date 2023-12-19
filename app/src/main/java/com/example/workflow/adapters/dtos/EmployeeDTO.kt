package com.example.workflow.adapters.dtos

import com.example.workflow.domain.entities.AttendanceRecord
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
    lateinit var schedule: List<TaskDTO>
    var workHours: Int = 0
    var workedHours: Int = 0
    lateinit var email : String
    lateinit var attendanceHistory: List<AttendanceRecord>

    companion object{
        fun toEmployee(employeeDTO: EmployeeDTO) : Employee{
            val employee : Employee = Employee(
                id = employeeDTO.id,
                employeeId = EmployeeID(employeeDTO.employeeId),
                name = EmployeeName(employeeDTO.name),
                surname = EmployeeSurname(employeeDTO.surname),
                workHours = EmployeeWorkHours(employeeDTO.workHours),
                workedHours = EmployeeWorkedHours(employeeDTO.workedHours),
                email = Email(employeeDTO.email),
                attendanceHistory = employeeDTO.attendanceHistory.toMutableList()
            )

            employee.schedule.schedule.addAll(employeeDTO.schedule.map { TaskDTO.toTask(it) })
            return employee
        }

        fun fromEmployee(employee: Employee) : EmployeeDTO{
            var employeeDTO = EmployeeDTO()
            employeeDTO.id = employee.id
            employeeDTO.name = employee.name.name
            employeeDTO.surname = employee.surname.surname
            employeeDTO.employeeId = employee.employeeId.id
            employeeDTO.schedule = employee.schedule.schedule.toList().map { TaskDTO.fromTask(it) }
            employeeDTO.workHours = employee.workHours.workHours
            employeeDTO.workedHours = employee.workedHours.workedHours
            employeeDTO.email = employee.email.email
            employeeDTO.attendanceHistory = employee.attendanceHistory.toList()

            return employeeDTO
        }
    }
}