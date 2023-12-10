package com.example.workflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme

@Composable
fun ControlCreateTaskCompose(navController:NavController){


    var taskName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(0.dp,15.dp,0.dp,10.dp)
                    ) {

                        Text(
                            text = "Crear Tarea",
                            modifier = Modifier,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 35.sp
                            )
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(25.dp,0.dp,20.dp,25.dp)
                                .fillMaxWidth()
                        ) {
                            UserTextField(
                                text = "Nombre de usuario",
                                taskName,
                                onTextValueChange = { taskName = it })
                            OutlinedTextField(
                                label =  { Text(text = "Descripción de la tarea")},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                value = description,
                                onValueChange = { description = it }
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .padding(0.dp,10.dp,0.dp,0.dp)
                                    .border(1.dp,Color.Gray,
                                        RoundedCornerShape(5))
                                    .height(200.dp)


                            ){
                                items(10){
                                    EmployeeTaskRow()
                                }
                            }
                            
                            FilledButton(onClick = { }, text = "Crear tarea",
                                Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(), GreenWorkFlow, Color.Black)
                        }

                    }
                }
            }
        )
    }
}

@Composable
fun EmployeeTaskRow(){

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(30.dp)
        ){
            Text("UserName")
        }
    }
}

@Preview
@Composable
fun ControlCreateTaskPreview(){
    ControlCreateTaskCompose(navController = rememberNavController())
}