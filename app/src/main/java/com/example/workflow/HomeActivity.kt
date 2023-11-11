package com.example.workflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.Pink40
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.theme.jua

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }

    @Composable
    fun Greeting(){
        WorkFlowTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueWorkFlow)
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(25.dp)){
                        Text(text = "WORKFLOW", modifier = Modifier.padding(10.dp),style= TextStyle(
                            color = Color.White, fontFamily = jua,
                            fontSize = 50.sp)
                        )
                        FilledButton (onClick = {}, text=getString(R.string.buttonHome1))
                        Text(text = getString(R.string.lorem_ipsum_small), modifier = Modifier.padding(10.dp), color = Color.White)
                        FilledButton (onClick = {}, text=getString(R.string.buttonHome2))
                        Text(text = getString(R.string.lorem_ipsum_small), modifier = Modifier.padding(10.dp), color = Color.White)
                    }
                    Text(text = getString(R.string.version_app), style = TextStyle(Color.Gray))

                }
            }
        }
    }

    @Composable
    fun FilledButton(onClick: () -> Unit, text: String){
        Button(onClick = { onClick() }, modifier = Modifier.padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = GreenWorkFlow, contentColor = Color.Black)) {
            Text(text = text )
        }
    }

    @Preview
    @Composable
    fun GreetingPreview() {
        Greeting()
    }


}