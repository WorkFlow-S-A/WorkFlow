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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme

@Composable
fun ControlAddTaskCompose(navController: NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments
    val employeeId = arguments?.getString("employeeId")

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
                    Text("Añadir tarea a $employeeId", modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 25.sp),
                        textAlign = TextAlign.Center
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()


                    ){
                        items(10){
                            //o task id, o el task, o lo que quieras
                            TaskRow("nameTask")
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun TaskRow(name:String){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                //Codigo para añadir la tarea
            },
        colors = CardDefaults.cardColors(
            containerColor = BlueWorkFlow,
            contentColor = Color.White
        )
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(name)
        }
    }
}

@Preview
@Composable
fun ControlAddTaskPreview(){
    ControlAddTaskCompose(rememberNavController())
}