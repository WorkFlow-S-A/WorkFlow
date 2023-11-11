package com.example.workflow.ports.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeLocalRepository
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import kotlinx.coroutines.flow.first

import java.util.UUID

class EmployeeService (
    private val employeeRemoteRepository: EmployeeRemoteRepository,
    private val employeeLocalRepository: EmployeeLocalRepository) : Service(){

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun saveEmployee(employee: Employee){

    }

    suspend fun getEmployee(id : UUID){
        val employee : Employee? = employeeRemoteRepository.getByIdStream(id).first()

        if(employee != null) {
            employeeLocalRepository.saveEmployee(employee = employee)
        }
    }


}
