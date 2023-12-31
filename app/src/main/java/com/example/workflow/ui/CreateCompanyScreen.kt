package com.example.workflow.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.R
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EmployeeWorkedHours
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.ui.customCompose.CustomClickableText
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.PasswordTextField
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.UUID

@Composable
fun CreateCompanyCompose(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()

    var companyName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }
    var userPasswordCheck by rememberSaveable { mutableStateOf("") }

    WorkFlowTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
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
                            .size(100.dp)
                            .padding(10.dp), contentDescription = null
                    )

                    Text(
                        text = "Bienvenido",
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 35.sp
                        )
                    )
                    UserTextField(
                        text = "Nombre de la empresa",
                        companyName,
                        onTextValueChange = { companyName = it })
                    UserTextField(
                        text = "Usuario administrador",
                        userName,
                        onTextValueChange = { userName = it })
                    EmailTextField(
                        text = "Correo electrónico",
                        userEmail,
                        onEmailValueChange = { userEmail = it })
                    PasswordTextField(
                        text = "Contraseña",
                        userPassword,
                        onPasswordValueChange = { userPassword = it })
                    PasswordTextField(
                        text = "Repetir contraseña",
                        userPasswordCheck,
                        onPasswordValueChange = { userPasswordCheck = it })
                    FilledButton(
                        onClick = {
                            if (userPassword == userPasswordCheck) {

                                coroutineScope.launch(Dispatchers.IO) {
                                    try {

                                        CompanyFirebaseRepository.createCompany(
                                            userEmail,
                                            userPassword,
                                            companyName
                                        )

                                        withContext(Dispatchers.Main) {
                                            navController.navigate("controlEmployees")
                                        }
                                    } catch (e: Exception) {
                                        Log.e("Internal Error", e.cause.toString())
                                    }

                                }
                            }

                        }, text = "Crear empresa", modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(), GreenWorkFlow, Color.Black
                    )

                    CustomClickableText(text = "¿Ya tienes o perteneces a una empresa?",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 10.dp),
                        onClick = {})
                    Text(
                        text = LocalContext.current.getString(R.string.version_app),
                        style = TextStyle(Color.Gray)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun CreateCompanyPreview(){
    CreateCompanyCompose(navController = rememberNavController())
}
