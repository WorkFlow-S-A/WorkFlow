package com.example.workflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.workflow.R
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddEmployeeCompose(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("") }
    var userSurname by remember { mutableStateOf("Surname") }
    var userDNI by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }





    WorkFlowTheme {

        Scaffold(
            topBar = { TopBarControl(onClick = { navController.popBackStack() }, visible = true) },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .padding(padding)
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
                                text = "Añadir empleado",
                                modifier = Modifier.padding(10.dp),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 35.sp
                                )
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(25.dp)
                                .fillMaxWidth()
                        ) {
                            UserTextField(
                                text = "Nombre de usuario",
                                userName,
                                onTextValueChange = { userName = it })
                            UserTextField("DNI", userDNI, onTextValueChange = { userDNI = it })
                            EmailTextField(
                                text = "Correo electrónico",
                                emailValue = email,
                                onEmailValueChange = { email = it })
                            FilledButton(onClick = {
                                coroutineScope.launch(Dispatchers.IO){
                                    val employee = Employee(
                                        name = EmployeeName(userName),
                                        surname = EmployeeSurname(userSurname),
                                        employeeId = EmployeeID(userDNI),
                                        email = Email(email),
                                        workHours = EmployeeWorkHours(0)
                                    )
                                    CompanyFirebaseRepository.addEmployeeToCompany(employee)

                                    withContext(Dispatchers.Main){
                                        navController.navigate("controlEmployees")
                                    }
                                }
                            }, text = "Crear empleado",
                                Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(), GreenWorkFlow, Color.Black)
                        }
                        Text(
                            text = LocalContext.current.getString(R.string.version_app),
                            style = TextStyle(
                                Color.Gray
                            )
                        )
                    }
                }
            }
        )
    }

}

@Preview
@Composable
fun AddEmployeePreview(){
    AddEmployeeCompose(navController = rememberNavController())
}