package com.example.workflow.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleControlEmployeeCompose(navController: NavController) {

    WorkFlowTheme {
        Scaffold(
            topBar = { TopBar() },
            content = {padding ->
                Column(modifier= Modifier.padding(padding)) {
                    Text("Funciona schedule")
                }
            },
            bottomBar = { BottomBar(navController) }
        )

    }





}