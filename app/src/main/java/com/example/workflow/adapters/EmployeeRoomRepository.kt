package com.example.workflow.adapters

import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeLocalRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class EmployeeRoomRepository : EmployeeLocalRepository{
    override suspend fun getEmployee(id: UUID): Flow<Employee?> {
        TODO("Not yet implemented")
    }

    override suspend fun saveEmployee(employee: Employee, needSync: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(employees: List<Employee>) {
        TODO("Not yet implemented")
    }

    override suspend fun getDesynchronizedEmployees(): List<Employee> {
        TODO("Not yet implemented")
    }
}