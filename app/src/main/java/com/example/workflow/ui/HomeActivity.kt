package com.example.workflow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.workflow.MainActivity
import com.example.workflow.R
import com.example.workflow.adapters.EmployeeFirebaseRepository
import com.example.workflow.adapters.EmployeeRoomRepository
import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSchedule
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EmployeeWorkedHours
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.theme.jua
import com.example.workflow.utils.InternetChecker
import java.lang.Exception
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    private val employeeService by lazy { EmployeeService.getService(EmployeeFirebaseRepository(),EmployeeRoomRepository(),this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
        val intent = Intent(this, EmployeeService::class.java)
        startService(intent)


    }

    @Composable
    fun Greeting(){
        WorkFlowTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueWorkFlow)
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(25.dp)){
                        Text(text = "WORKFLOW", modifier = Modifier.padding(10.dp),style= TextStyle(
                            color = Color.White, fontFamily = jua,
                            fontSize = 50.sp)
                        )

                        FilledButton (onClick = {

                            lifecycleScope.launch {
                                save()
                            }
                                                }, text=getString(R.string.buttonHome1))
                        Text(text = getString(R.string.lorem_ipsum_small), modifier = Modifier.padding(10.dp), color = Color.White)
                        FilledButton (onClick = {}, text=getString(R.string.buttonHome2))
                        Text(text = getString(R.string.lorem_ipsum_small), modifier = Modifier.padding(10.dp), color = Color.White)
                    }
                    Text(text = getString(R.string.version_app), style = TextStyle(Color.Gray))

                }
            }
        }
    }

    @Composable
    fun FilledButton(onClick: () -> Unit, text: String){
        Button(onClick = { onClick() }, modifier = Modifier.padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = GreenWorkFlow, contentColor = Color.Black)) {
            Text(text = text )
        }
    }

    @Preview
    @Composable
    fun GreetingPreview() {
        Greeting()
    }

    suspend fun save(){
        var employee : Employee? = null
        try {
            employee = Employee(
                name = EmployeeName("Paco"),
                surname = EmployeeSurname("Perez"),
                email = Email("pacoperez@gmail.com"),
                employeeId = EmployeeID("12345678"),
                workedHours = EmployeeWorkedHours(8),
                workHours = EmployeeWorkHours(8)
            )

            employee.schedule.schedule.add(Task(

                name = TaskName("Trabajar"),
                description = TaskDescription("Trabajar"),
                done = false,
                endHour = EndHour(Calendar.getInstance()),
                startHour = StartHour(Calendar.getInstance())

            ))

        }catch (e : Exception){
            Log.d("Error", e.toString())
        }
        if(employee != null){
            employeeService.saveEmployee(employee);
        }

    }


}