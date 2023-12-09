package com.example.workflow.adapters.repositories.firebase

import android.util.Log
import com.example.workflow.App
import com.example.workflow.domain.entities.Employee
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
            }else{
                Log.w("Employee operation", "User did not add")
            }
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

        suspend fun findCompany(employeeId : String) : Boolean{
            var isAdmin : Boolean = false
            employeeMappedDb.document(employeeId).get()
                .addOnSuccessListener{ document ->
                    Log.d("FireBase Auth", "Checking user")
                    if(document.exists()){
                        class Data(val company : String)
                        currentCompanyId = document.toObject(Data::class.java)?.company ?: ""

                    }else{
                        currentCompanyId = employeeId
                        isAdmin = true
                    }

                }
                .addOnFailureListener {

                    Log.w("Firebase AUTH", it.cause)
                }.await()
            isAdmin
            return isAdmin
        }

    }


}