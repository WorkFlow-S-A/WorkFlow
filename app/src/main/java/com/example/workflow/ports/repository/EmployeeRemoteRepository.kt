package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeName
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface EmployeeRemoteRepository {

    suspend fun insertEmployee(employee : Employee)

    suspend fun getAllEmployeesStream() : Flow<List<Employee>>

    suspend fun getByIdStream(id: UUID) : Flow<Employee?>

    suspend fun deleteEmployee(employee : Employee)

    suspend fun updateEmployeeField(id: String, newData : String, fieldType : String )




}