package com.example.workflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.Task
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.launch

@Composable
fun ControlAddTaskCompose(navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments
    val employeeId = arguments?.getString("employeeId")
    var tasks by remember { mutableStateOf(emptyList<Task>()) }

    var employeeName by remember { mutableStateOf("Name") }
    LaunchedEffect(Unit) {
        tasks = App.instance.taskService.getAllTasks()
    }
    if(employeeId != null){
        LaunchedEffect(Unit){

            val employee : Employee = App.instance.employeeService.getEmployee(employeeId)
            employeeName = employee.name.name

        }
    }


    WorkFlowTheme {

        Scaffold(
            topBar = { TopBarControl(onClick = { navController.popBackStack() }, visible = true) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Text("Añadir tarea a $employeeName", modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 25.sp),
                        textAlign = TextAlign.Center
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()


                    ){

                        items(tasks.size){ index->

                            TaskRow1(employeeId!!, tasks[index])
                        }

                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(WorkFlowScreen.ControlCreateTask.name) },
                    containerColor = GreenWorkFlow
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        )
    }
}


@Composable
fun TaskRow1(employeeId : String, task:Task){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                App.instance.employeeService.addTaskToEmployee(employeeId, task)
            },
        colors = CardDefaults.cardColors(
            containerColor = BlueWorkFlow,
            contentColor = Color.White
        )
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(task.name.name)
        }
    }
}

@Preview
@Composable
fun ControlAddTaskPreview(){
    ControlAddTaskCompose(rememberNavController())
}