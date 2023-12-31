package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.App
import com.example.workflow.adapters.dtos.TaskDTO
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class CompanyFirebaseRepository {

    companion object{

        private val db = FirebaseFirestore.getInstance().collection("Company")
        private val employeeMappedDb = FirebaseFirestore.getInstance().collection("EmployeeMappedCompany")
        private lateinit var currentCompanyId : String


        suspend fun createCompany(email : String, password : String, companyName : String){
            val uid = App.instance.firebaseAuthentication.createCompanyAccount(email, password)
            if(uid != null){
                db.document(uid).set(mapOf("companyName" to companyName)).await()
                currentCompanyId = uid
            }


        }

        /**
         * Remember that the password of user is {name}_{employeeId}
         * */
        suspend fun addEmployeeToCompany(employee: Employee){
            val password = employee.name.name + "_" + employee.employeeId.id
            val uid = App.instance.firebaseAuthentication.createEmployeeAccount(employee.email.email, password)

            if(uid != null){
                employee.id = uid
                App.instance.employeeService.saveEmployee(employee)
                employeeMappedDb.document(employee.id).set(mapOf("company" to getCurrentCompanyId()))
                /*
                db.document(currentCompanyId).collection("EmployeePrivacy")
                    .document(employee.id).set(mapOf("privacy" to password))
                    */

            }else{
                Log.w("Employee operation", "User did not add")
            }
        }

        suspend fun deleteEmployeeFromCompany(id: String){
            db.document(currentCompanyId).collection("Employees")
                .document(id).delete().await()

        }

        fun deleteCompany(companyID : String){
            db.document(companyID).delete()
        }

        private fun updateCompanyId(){
            currentCompanyId = App.instance.firebaseAuthentication.getCurrentUser().uid
        }

        fun getCurrentCompanyId() : String{
            updateCompanyId()
            return currentCompanyId
        }

        suspend fun getCurrentCompanyName() : String{
            var name : String = ""
            db.document(currentCompanyId).get()
                .addOnSuccessListener {
                    if (it.exists()){
                        name = it.getString("companyName")!!
                    }
                }.await()
            return name
        }

        suspend fun findCompany(employeeId: String): Boolean {
            var isAdmin: Boolean = false

            try {
                val document = employeeMappedDb.document(employeeId).get().await()
                Log.d("FireBase Auth", "Checking user")

                if (document.exists()) {
                    class Data(val company: String)
                    currentCompanyId = document.toObject(Data::class.java)?.company ?: ""
                } else {
                    currentCompanyId = employeeId
                    isAdmin = true
                }
            } catch (e: Exception) {
                Log.w("Firebase AUTH", e)
            }

            return isAdmin
        }

        suspend fun updateQr(number : String){

            db.document(currentCompanyId).set(mapOf(
                "currentQrCode" to number,
                "companyName" to getCurrentCompanyName()
            ))
        }


    }


}