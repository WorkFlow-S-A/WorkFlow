package com.example.workflow.ports.repository

import com.example.workflow.domain.entities.Employee
import java.util.Objects

interface EmployeeLocalRepository {

    suspend fun saveEmployee(employee: Employee)

    suspend fun deleteEmployee(employee: Employee)
}