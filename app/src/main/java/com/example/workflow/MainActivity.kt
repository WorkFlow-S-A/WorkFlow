package com.example.workflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.WorkFlowApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkFlowTheme {
                WorkFlowApp()
            }
        }
    }




    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WorkFlowTheme {
            WorkFlowApp()
        }
    }


}