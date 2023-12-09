package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import kotlinx.coroutines.flow.Flow
import java.util.Objects
import java.util.UUID

interface EmployeeLocalRepository {

    suspend fun getEmployee(id : String) : Flow<Employee?>

    suspend fun saveEmployee(employee: Employee, needSync : Boolean)

    suspend fun deleteEmployee(employee: Employee)

    suspend fun saveAll(employees : List<Employee>)
    
    suspend fun getDesynchronizedEmployees() : List<Employee>
}