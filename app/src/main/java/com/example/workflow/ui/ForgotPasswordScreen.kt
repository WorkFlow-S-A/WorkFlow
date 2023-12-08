package com.example.workflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.R
import com.example.workflow.ui.customCompose.CustomClickableText
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.theme.WorkFlowTheme

@Composable
fun ForgotPasswordCompose(navController: NavController) {

    var userEmail by remember { mutableStateOf("") }

    WorkFlowTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(25.dp)
                ) {
                    Text(
                        text = "Restablecer contraseña",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 35.sp
                        )
                    )
                    EmailTextField("Correo Electrónico",userEmail, onEmailValueChange = {userEmail = it})
                    FilledButton(onClick = { /*TODO*/ }, text = "MANDAR EMAIL", modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), Color.White,Color.Red)
                    CustomClickableText(text = "Volver a iniciar sesión", modifier = Modifier.align(Alignment.Start).padding(top=10.dp),{navController.navigate("logIn")})



                }
                Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(
                    Color.Gray)
                )
            }

        }
    }
}

@Preview
@Composable
fun ForgotPasswordPreview(){
    ForgotPasswordCompose(rememberNavController())
}