package com.example.workflow.adapters.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class FirebaseAuthentication : Service(){
    private lateinit var auth: FirebaseAuth
    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth

    }

    /**
     * @return UID of new user
     */
    private suspend fun createAccount(email:String, password:String) : String?{
        var newUserUid : String? = null

        auth.createUserWithEmailAndPassword(email ,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    newUserUid = task.result.user?.uid
                    Log.d("Firebase AUTH", "createUserWithEmail:success")

                }else{
                    Log.w("Firebase AUTH", "createUserWithEmail:failure",task.exception)
                }
            }.addOnFailureListener {
                Log.e("Firebase AUTH",it.message?:"Internal Error")
            }
            .await()


        //TODO : sendEmailVerification


        return newUserUid

    }

    suspend fun createCompanyAccount(email:String, password:String) : String?{
        return createAccount(email , password)
    }

    suspend fun createEmployeeAccount(email:String, password:String) : String?{
        val currentUser : FirebaseUser = auth.currentUser!!
        val uid = createAccount(email, password)
        currentUser.uid
        auth.updateCurrentUser(currentUser)
        return uid
    }


    suspend fun logIn(email:String, password:String) : String{
        var uid :String = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                uid = it.user?.uid ?: ""

            }
            .addOnFailureListener {
                Log.w("Firebase AUTH", it.cause?.toString() ?: "")

            }
            .await()

        uid
        return uid
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