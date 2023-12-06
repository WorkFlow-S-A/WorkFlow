package com.example.workflow.ports.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.workflow.utils.InternetChecker
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ports.repository.EmployeeLocalRepository
import com.example.workflow.ports.repository.EmployeeRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first

import java.util.UUID

class EmployeeService() : Service(){


    companion object{

        private var instance : EmployeeService? = null
        var employeeRemoteRepository: EmployeeRemoteRepository? = null
        var employeeLocalRepository: EmployeeLocalRepository? = null
        private var thisContext : Context? = null
        fun getService(remoteRepository: EmployeeRemoteRepository,
                       localRepository: EmployeeLocalRepository,
                       context: Context) : EmployeeService{
            if(instance == null) {
                thisContext = context
                employeeRemoteRepository = remoteRepository
                employeeLocalRepository = localRepository
                return EmployeeService()
            }

            return instance!!

        }
    }

    suspend fun saveEmployee(employee: Employee){
        if(InternetChecker.checkConnectivity(thisContext)){
            employeeRemoteRepository?.insertEmployee(employee = employee)
           // employeeLocalRepository?.saveEmployee(employee = employee, false)
        }else{
            employeeLocalRepository?.saveEmployee(employee = employee, true)
        }
    }

    suspend fun getEmployee(id : UUID) : Employee{
        /*var employeeStream : Flow<Employee?>? = employeeLocalRepository?.getEmployee(id = id)
        if (employeeStream != null) {
            if(employeeStream.count() > 0){
                return employeeStream.first()?: throw InternalError("There was a problem while the object was being found.")
            }
        }

        if (employeeStream != null) {*/
            var employeeStream = employeeRemoteRepository?.getByIdStream(id)
            if(employeeStream?.count()!! > 0) {
                val employee : Employee? = employeeStream.first()
                if(employee != null){
                    employeeLocalRepository?.saveEmployee(employee = employee, false)
                    return employee
                } else throw InternalError("There was a problem while the object was being found.")

            } else{
                throw IllegalArgumentException("Not found")
            }
       // }

        throw InternalError("No Database found")
    }

    suspend fun getAllEmployees() : List<Employee>{
        //TODO : Try the listener of remote DB to take data from Local DB directly

        var employees : List<Employee> = employeeRemoteRepository!!.getAllEmployeesStream().first()

        employeeLocalRepository?.saveAll(employees)

        return employees
    }

    suspend fun deleteEmployee(employee : Employee){
        //TODO : See the return errors that throw DB's
        if(InternetChecker.checkConnectivity(thisContext)){
            employeeLocalRepository?.deleteEmployee(employee)
        }
        InternetChecker.checkConnectivity(thisContext)
        employeeRemoteRepository?.deleteEmployee(employee)
    }

    suspend fun updateEmployee(employee: Employee){
        if(InternetChecker.checkConnectivity(thisContext)){
            employeeRemoteRepository?.updateEmployee(employee)
            employeeLocalRepository?.saveEmployee(employee, false)
        }else employeeLocalRepository?.saveEmployee(employee, true)



    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}
