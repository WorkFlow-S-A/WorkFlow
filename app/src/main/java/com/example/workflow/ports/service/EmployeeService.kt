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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

import java.util.UUID

class EmployeeService : Service(){
    private val internetChecker : InternetChecker = App.instance.internetChecker

    companion object{

        private var instance : EmployeeService? = null
        var employeeRemoteRepository: EmployeeRemoteRepository? = null
        var employeeLocalRepository: EmployeeLocalRepository? = null
        private var thisContext : Context? = null
        fun getService(remoteRepository: EmployeeRemoteRepository? = null,
                       localRepository: EmployeeLocalRepository? = null,
                       context: Context? = null ) : EmployeeService{
            if(instance == null) {
                thisContext = context
                employeeRemoteRepository = remoteRepository
                employeeLocalRepository = localRepository
                return EmployeeService()
            }

            return instance!!

        }
    }

    /**
     * Remember that the password of user is {name}_{employeeId}
     * */
    suspend fun saveEmployee(employee: Employee){
        if(internetChecker.checkConnectivity()){
            val password = employee.name.name + "_" + employee.employeeId.id
            val uid = App.instance.firebaseAuthentication.createUser(employee.email.email, password)

            if(uid != null){
                employee.id = uid
                employeeRemoteRepository?.insertEmployee(employee = employee)
            }else{
                Log.w("Employee operation", "User did not add")
            }

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
                    //employeeLocalRepository?.saveEmployee(employee = employee, false)
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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}
