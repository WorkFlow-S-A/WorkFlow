package com.example.workflow.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.adapters.utils.QrGenerator
import com.example.workflow.domain.entities.Employee
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlEmployeesCompose(navController: NavController){
    val coroutineScope = rememberCoroutineScope()

    // Crear un launcher para abrir el navegador web
    val openUrlLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Puedes manejar el resultado si es necesario
    }
    var employees by remember { mutableStateOf(emptyList<Employee>()) }
    LaunchedEffect(Unit) {
        try {
            employees = App.instance.employeeService.getAllEmployees()
        } catch (e: Exception) {
            Log.e("Error al obtener empleados", e.toString())
        }

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
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Empleados",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium )

                        FilledButton(
                            onClick = {
                                coroutineScope.launch {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(QrGenerator.getQr()))
                                    // Iniciar la actividad del navegador web utilizando el launcher
                                    openUrlLauncher.launch(intent)
                                }
                            },
                            text = "QR",
                            modifier = Modifier,
                            containerColor = GreenWorkFlow,
                            contentColor = Color.Black
                        )

                    }


                    LazyColumn (modifier = Modifier
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                        .fillMaxSize()){


                            items(employees.size) { index ->
                                EmployeeRow(
                                    employees[index],
                                    navController
                                )

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
fun EmployeeRow(employee : Employee, navController: NavController){
    //Log.d("Employees", employee?.id)

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

        IconButton(onClick = { navController.navigate("controlEmployeesProfile/${employee.id}") }){
            Icon(tint = Color.White, imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Editar empleado")
        }


    }
}

@Preview
@Composable
fun ControlEmployeesPreview(){
    ControlEmployeesCompose(navController = rememberNavController())
}