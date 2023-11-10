package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Employee
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface EmployeeRemoteRepository {

    suspend fun insertEmployee(employee : Employee)

    fun getAllEmployeesStream() : Flow<List<Employee>>

    fun getByIdStream(id: UUID) : Flow<Employee?>

    suspend fun deleteEmployee(employee : Employee)

    suspend fun updateEmployee(employee: Employee)


}