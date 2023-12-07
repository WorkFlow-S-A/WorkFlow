package com.example.workflow.adapters.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.workflow.App
import com.example.workflow.ports.service.EmployeeService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class FirebaseAuthentication : Service(){
    private lateinit var auth: FirebaseAuth
    private lateinit var userID : String


    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth

    }

    /**
     * @return UID of new user
     */
    suspend fun createUser(email:String , password:String) : String?{
        var newUserUid : String? = null
        auth.createUserWithEmailAndPassword(email ,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    newUserUid = task.result.user?.uid
                    Log.d("Firebase AUTH", "createUserWithEmail:success")

                }else{
                    Log.w("Firebase AUTH", "createUserWithEmail:failure",task.exception)
                }
            }.await()

        //TODO : sendEmailVerification

        return newUserUid

    }



    fun getCurrentUser() : FirebaseUser{
        return auth.currentUser?: throw Error("There are not users")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun chagePassword(){
        TODO("Change password fun")
    }



}