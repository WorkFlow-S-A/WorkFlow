package com.example.workflow.adapters.repositories.room

import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSchedule
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EmployeeWorkedHours
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ports.repository.EmployeeLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class EmployeeRoomRepository(private val employeeDao: EmployeeRoomDao): EmployeeLocalRepository {
    override suspend fun getEmployee(id: UUID): Flow<Employee?> {
        val employee = employeeDao.getEmployee(id.toString())
        return employee.map { roomEmployee ->
            roomEmployee.toEmployee()
        }
    }

    override suspend fun saveEmployee(employee: Employee, needSync: Boolean) {
        employeeDao.insert(employee.toEmployeeRoomEntity(needSync))
    }

    override suspend fun deleteEmployee(employee: Employee) {
        employeeDao.delete(employee.toEmployeeRoomEntity(false))
    }

    override suspend fun saveAll(employees: List<Employee>) {
        val roomEmployees = employees.map { it.toEmployeeRoomEntity(false) }
        employeeDao.insertAll(roomEmployees)
    }

    override suspend fun getDesynchronizedEmployees(): List<Employee> {
        val roomDesynchronizedEmployees = employeeDao.getAllDesynchronizedEmployees()
        return roomDesynchronizedEmployees.map { it.toEmployee() }
    }

    private fun RoomEmployee.toEmployee(): Employee {

        return Employee(
            id = this.id,
            employeeId = EmployeeID(this.employeeId),
            name = EmployeeName(this.name),
            surname = EmployeeSurname(this.surname),
            schedule = EmployeeSchedule(RoomEmployee.convertJsonToSchedule(this.schedule)),
            workHours = EmployeeWorkHours(this.workHours),
            workedHours = EmployeeWorkedHours(this.workedHours),
            email = Email(this.email)
        )
    }

    private fun Employee.toEmployeeRoomEntity(needSync: Boolean): RoomEmployee {
        return RoomEmployee(
            id = this.id,
            employeeId = this.employeeId.id,
            name = this.name.name,
            surname = this.surname.surname,
            schedule = RoomEmployee.convertScheduleToJson(this.schedule.schedule),
            workHours = this.workHours.workHours,
            workedHours = this.workedHours.workedHours,
            email = this.email.email,
            needSync = needSync
        )
    }
}