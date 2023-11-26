package com.example.workflow.adapters.repositories

import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class EmployeeFirebaseRepository() : EmployeeRemoteRepository{
    override suspend fun insertEmployee(employee: Employee) {

    }

    override fun getAllEmployeesStream(): Flow<List<Employee>> {
        TODO("Not yet implemented")
    }

    override fun getByIdStream(id: UUID): Flow<Employee> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }
}