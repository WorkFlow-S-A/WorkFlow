package com.example.workflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.ui.theme.WorkFlowTheme

@Composable
fun TaskEmployeeCompose(navController: NavController) {
    WorkFlowTheme {
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            HeaderTask()
        }
    }
}

@Composable
fun HeaderTask(){
    Row {
        Text(
            text = "Saturday 20, May 2023",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Previous"
            )
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
}



@Preview
@Composable
fun TaskEmployeeComposePreview() {
    TaskEmployeeCompose(navController = rememberNavController())
    
}
