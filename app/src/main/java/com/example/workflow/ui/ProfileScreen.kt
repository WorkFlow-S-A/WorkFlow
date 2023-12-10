package com.example.workflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.R
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TextFieldCustom
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCompose(navController: NavController) {

    val employeeId = App.instance.currentEmployeeUid
    val coroutineScope = rememberCoroutineScope()



    var email by remember { mutableStateOf("jpereiro1@gmail.com") }
    var name by remember { mutableStateOf("User name") }
    var uid by remember { mutableStateOf("12345678X") }
    var workHours by remember { mutableIntStateOf(35) }
    var workedHours by remember { mutableIntStateOf(35) }

    if(employeeId != null){
        LaunchedEffect(Unit) {
            val employee = App.instance.employeeService.getEmployee(employeeId)
            email = employee.email.email
            name = employee.name.name
            uid = employee.employeeId.id
            workHours = employee.workHours.workHours
            workedHours = employee.workedHours.workedHours

        }
    }

    WorkFlowTheme {
        Scaffold(
            topBar = { TopBar() },
            content = {padding ->
                Column(modifier= Modifier.padding(padding)) {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(152, 177, 255))) {
                        IconButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = {}
                        ) {
                            Icon(tint = Color.Black, imageVector = Icons.Default.Edit, contentDescription = "Boton para editar la foto de perfil")
                        }
                        Image(
                            modifier = Modifier
                                .width(150.dp)
                                .align(CenterHorizontally)
                                .padding(15.dp),
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null
                        )

                        Text(name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Column (modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Column(){
                            TextFieldCustom("Email","jpereiro1@gmail.com")
                            TextFieldCustom("DNI","12345678X")
                        }


                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier= Modifier.fillMaxWidth().padding(0.dp,15.dp,0.dp,0.dp)){

                            Row(horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically){
                                Text("Horas estimadas")
                                Box(
                                    modifier = Modifier
                                        .background(Color(152, 177, 255))
                                        .clip(RoundedCornerShape(10.dp)),
                                    Center
                                ){
                                    //Poner las horas
                                    Text("$workHours h",modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .padding(15.dp))
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically){
                                Text("Horas realizadas")
                                Box(
                                    modifier = Modifier
                                        .background(Color(152, 177, 255)),

                                    Center
                                    ){
                                    //Poner las horas
                                    Text("$workedHours h",modifier = Modifier
                                        .padding(15.dp)
                                        .clip(RoundedCornerShape(50)))
                                }
                            }
                        }

                        FilledButton(onClick = { /*TODO*/ }, text = "Cambiar contraseña", modifier =Modifier.padding(10.dp),
                            GreenWorkFlow,Color.Black)
                        Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(
                            Color.Gray)
                        )


                    }
                }
                      },
            bottomBar = { BottomBar(navController) }
        )
    }

}

@Preview
@Composable
fun ProfileComposePreview(){
    ProfileCompose(navController = rememberNavController())
}