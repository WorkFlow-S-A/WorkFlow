package com.example.workflow

import android.app.Application
import com.example.workflow.adapters.auth.FirebaseAuthentication
import com.example.workflow.adapters.repositories.firebase.EmployeeFirebaseRepository
import com.example.workflow.adapters.repositories.room.EmployeeRoomRepository
import com.example.workflow.adapters.repositories.room.LocalDatabase
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.utils.InternetChecker

class App : Application() {
    private lateinit var localDatabase: LocalDatabase

    val employeeService by lazy {
        EmployeeService.getService(
            EmployeeFirebaseRepository(),
            EmployeeRoomRepository(localDatabase.employeeDao()),
            context = this
        )
    }

    val internetChecker by lazy {
        InternetChecker.getInstance(this)
    }

    val firebaseAuthentication by lazy{
        FirebaseAuthentication()
    }

    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        localDatabase = LocalDatabase.getDatabase(this)
        firebaseAuthentication.onCreate()
    }
}