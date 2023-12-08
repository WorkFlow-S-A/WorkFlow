package com.example.workflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlEmployeesCompose(navController: NavController){
    WorkFlowTheme {
        Scaffold(
            topBar = {TopBarControl({  },false)},
            content = {padding ->
                Column(modifier= Modifier
                    .padding(padding)
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(10.dp)
                ){
                    Text("Empleados",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium )

                    LazyColumn (modifier = Modifier
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                        .fillMaxSize()){
                        //LLAMAR A LA BASE DE DATOS Y OBTENER todos los empleados DE ESE DIA PARA MOSTRARLO AQUI
                        items(5) {
                            EmployeeRow(
                                navController
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(WorkFlowScreen.ControlAddEmployee.name) }, containerColor = GreenWorkFlow) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

        )
    }
}

@Composable
fun EmployeeRow(/*emloyee*/navController: NavController){


    Row(modifier = Modifier
        .padding(bottom = 8.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(BlueWorkFlow)
        .fillMaxSize()

        .padding(15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        //ICONO DEL USUARIO
        Row(){
            //Image()
            Text("User Name", color = Color.White)
        }

        IconButton(onClick = { navController.navigate("controlEmployeesProfile"+"/123") }){
            Icon(tint = Color.White, imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Editar empleado")
        }


    }
}

@Preview
@Composable
fun ControlEmployeesPreview(){
    ControlEmployeesCompose(navController = rememberNavController())
}