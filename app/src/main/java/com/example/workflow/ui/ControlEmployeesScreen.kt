package com.example.workflow.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlEmployeesCompose(navController: NavController){
    var employees by remember { mutableStateOf(emptyList<Employee>()) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Log.d("ENTRO EN LA CORR" , "")
        employees = App.instance.employeeService.getAllEmployees()
    }



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

                        items(employees.size) {
                            employees.forEach{
                                EmployeeRow(
                                    it,
                                    navController
                                )
                            }
                        }

                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate(WorkFlowScreen.ControlAddEmployee.name)
                }, containerColor = GreenWorkFlow) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

        )
    }
}

@Composable
fun EmployeeRow(employee : Employee,navController: NavController){


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
            Text(employee.name.name, color = Color.White)
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