package com.example.workflow.adapters.repositories.room

import com.example.workflow.domain.entities.AttendanceRecord
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
    override suspend fun getEmployee(id: String): Flow<Employee?> {
        val employee = employeeDao.getEmployee(id)
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

    override suspend fun checkIn(employee: Employee, checkInTime: String) {
        val currentDayAttendance = AttendanceRecord(checkInTime, "")
        employee.attendanceHistory.add(currentDayAttendance)
        if (employee.attendanceHistory.size > 31) {
            employee.attendanceHistory = employee.attendanceHistory.subList(employee.attendanceHistory.size - 31, employee.attendanceHistory.size).toMutableList()
        }
        employeeDao.update(employee.toEmployeeRoomEntity(false))
    }

    override suspend fun checkOut(employee: Employee, checkOutTime: String) {
        val lastRecord = employee.attendanceHistory.lastOrNull()
        if (lastRecord != null && lastRecord.checkOutTime.isBlank()) {
            lastRecord.checkOutTime = checkOutTime
        }
        employeeDao.update(employee.toEmployeeRoomEntity(false))
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
            email = Email(this.email),
            attendanceHistory = RoomEmployee.convertJsonToAttendanceHistory(this.attendanceHistory)
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
            attendanceHistory = RoomEmployee.convertAttendanceHistoryToJson(this.attendanceHistory),
            needSync = needSync
        )
    }
}