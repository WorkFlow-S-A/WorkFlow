package com.example.workflow.ports.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.workflow.App
import com.example.workflow.utils.InternetChecker
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeLocalRepository
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

import java.util.UUID

class EmployeeService : Service(){
    private val internetChecker : InternetChecker = App.instance.internetChecker

    companion object{

        private var instance : EmployeeService? = null
        lateinit var employeeRemoteRepository: EmployeeRemoteRepository
        lateinit var employeeLocalRepository: EmployeeLocalRepository
        fun getService(remoteRepository: EmployeeRemoteRepository,
                       localRepository: EmployeeLocalRepository) : EmployeeService{
            if(instance == null) {
                employeeRemoteRepository = remoteRepository
                employeeLocalRepository = localRepository
                instance = EmployeeService()
            }

            return instance!!

        }
    }

    /**
     * Remember that the password of user is {name}_{employeeId}
     * */
    suspend fun saveEmployee(employee: Employee){
        if(internetChecker.checkConnectivity()){

            employeeRemoteRepository.insertEmployee(employee = employee)

            employeeLocalRepository.saveEmployee(employee = employee, false)
        }else{
            employeeLocalRepository.saveEmployee(employee = employee, true)
        }
    }

    suspend fun getEmployee(id : String) : Employee{
        val employeeStream : Flow<Employee?>? = employeeLocalRepository.getEmployee(id = id)
        val employee = employeeStream?.firstOrNull()
        if (employee != null) {
            Log.d("pasaber", "Hemos llegado")
            return employee
        }


        if (employeeStream != null) {
            var employeeStream = employeeRemoteRepository.getByIdStream(id)
            if(employeeStream.count() > 0) {
                val employee : Employee? = employeeStream.first()
                if(employee != null){
                    employeeLocalRepository?.saveEmployee(employee = employee, false)
                    return employee
                } else throw InternalError("There was a problem while the object was being found.")

            } else{
                throw IllegalArgumentException("Not found")
            }
        }

        throw InternalError("No Database found")
    }

    suspend fun getAllEmployees() : List<Employee>{
        //TODO : Try the listener of remote DB to take data from Local DB directly

        var employees : List<Employee> = employeeRemoteRepository.getAllEmployeesStream().first()

        employeeLocalRepository.saveAll(employees)

        return employees
    }

    suspend fun deleteEmployee(employee : Employee){
        //TODO : See the return errors that throw DB's
        if(internetChecker.checkConnectivity()){
            employeeLocalRepository?.deleteEmployee(employee)
        }
        internetChecker.checkConnectivity()
        employeeRemoteRepository?.deleteEmployee(employee)
    }

    suspend fun updateEmployee(employee: Employee, field : String, newData : String){
        if(internetChecker.checkConnectivity()){
            employeeRemoteRepository?.updateEmployeeField(
                id = employee.id,
                fieldType = field,
                newData = newData)
            employeeLocalRepository?.saveEmployee(employee, false)
        }else employeeLocalRepository?.saveEmployee(employee, true)



    }

    suspend fun checkIn(employee: Employee, checkInTime: String) {
        if (internetChecker.checkConnectivity()) {
            employeeRemoteRepository.checkIn(employee, checkInTime)
            employeeLocalRepository.checkIn(employee, checkInTime)
        } else
            employeeLocalRepository.checkIn(employee, checkInTime)
    }

    suspend fun checkOut(employee: Employee, checkOutTime: String) {
        if (internetChecker.checkConnectivity()) {
            employeeRemoteRepository.checkOut(employee, checkOutTime)
            employeeLocalRepository.checkOut(employee, checkOutTime)
        } else
            employeeLocalRepository.checkOut(employee, checkOutTime)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}
