package com.example.workflow.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.R
import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EmployeeWorkedHours
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.theme.jua
import com.example.workflow.utils.InternetChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun StartCompose(navController: NavController){
    WorkFlowTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueWorkFlow)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(25.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        modifier = Modifier
                            .size(125.dp)
                            .padding(10.dp), contentDescription = null
                    )

                    Text(
                        text = "WORKFLOW",
                        modifier = Modifier.padding(10.dp),
                        style = TextStyle(
                            color = Color.White, fontFamily = jua,
                            fontSize = 50.sp
                        )
                    )
                    FilledButton(onClick = {
                                          runBlocking {
                                              mySuspendFunction(App.instance.employeeService)
                                          }
                        navController.navigate("logIn")
                }, text = LocalContext.current.getString(R.string.buttonHome1),Modifier.padding(10.dp))
                    Text(
                        text = LocalContext.current.getString(R.string.lorem_ipsum_small),
                        modifier = Modifier.padding(10.dp),
                        color = Color.White
                    )
                    FilledButton(onClick = {navController.navigate("createCompany")}, text = LocalContext.current.getString(R.string.buttonHome2),Modifier.padding(10.dp))
                    Text(
                        text = LocalContext.current.getString(R.string.lorem_ipsum_small),
                        modifier = Modifier.padding(10.dp),
                        color = Color.White
                    )
                }
                Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(
                    Color.Gray)
                )
            }

        }
    }
}



@Preview
@Composable
fun StartComposePreview(){
    StartCompose(navController = rememberNavController())
}

private suspend fun mySuspendFunction(employeeService: EmployeeService) {
    var employee : Employee
    try {
        val employeeNew = employeeService.getEmployee(UUID.fromString("baf4fa96-b9b8-4721-a2e5-38ef298a0b05"))
        employee = employeeNew
        employee = Employee(
            employeeId = EmployeeID("12345678"),
            name = EmployeeName("Pedro"),
            surname = EmployeeSurname("Sanchez"),
            workHours = EmployeeWorkHours(1),
            workedHours = EmployeeWorkedHours(1),
            email = Email("pedro.sanchez@gmail.com")
        )

    }catch (e : Exception){
        if(e.cause != null){
            e.cause!!.printStackTrace()
        }
        Log.d("ERROOOOOR",e.toString())
    }
}