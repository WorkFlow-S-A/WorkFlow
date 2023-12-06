package com.example.workflow

import android.app.Application
import com.example.workflow.adapters.repositories.firebase.EmployeeFirebaseRepository
import com.example.workflow.adapters.repositories.room.EmployeeRoomRepository
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.utils.InternetChecker

class App : Application() {
    val employeeService by lazy {
        EmployeeService.getService(
            EmployeeFirebaseRepository(),
            EmployeeRoomRepository(),
            this
        )
    }
    val internetChecker by lazy {
        InternetChecker.getInstance(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}