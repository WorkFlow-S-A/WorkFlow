package com.example.workflow

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workflow.ports.service.EmployeeService
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.WorkFlowApp
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    private val db = FirebaseFirestore.getInstance().collection("users")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkFlowTheme {
                WorkFlowApp()
            }
        }
    }



                
        //val intent = Intent(this, EmployeeService::class.java)
        //startService(intent)


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WorkFlowTheme {
            WorkFlowApp()
        }
    }

