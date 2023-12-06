package com.example.workflow.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.text.ClickableText

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.workflow.ui.theme.WorkFlowTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.R
import com.example.workflow.ui.customCompose.CustomClickableText
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.PasswordTextField
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.jua

@Composable
fun LogInCompose(navController: NavController) {

    var userName by remember { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }


    WorkFlowTheme{
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(25.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp), contentDescription = null
                    )

                    Text(
                        text = "Inicia sesión",
                        modifier = Modifier.padding(10.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 35.sp
                        )
                    )
                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(25.dp)
                        .fillMaxWidth()
                ){
                    val context = LocalContext.current
                    UserTextField(text = "Usuario",userName, onTextValueChange = { userName = it})
                    PasswordTextField(text="Contraseña",userPassword, onPasswordValueChange = {userPassword = it})
                    FilledButton(onClick =
                        {   Toast.makeText(context,userName,Toast.LENGTH_LONG).show()
                            navController.navigate(WorkFlowScreen.TaskEmployee.name)
                        }, text = "ENTRAR",
                        Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth())
                    CustomClickableText(text = "¿Olvidaste la contraseña?", modifier = Modifier.align(Alignment.Start).padding(top=10.dp), onClick = {navController.navigate("forgotPassword")})
                    CustomClickableText(text = "Crear una empresa", modifier = Modifier.align(Alignment.Start).padding(top=10.dp), onClick = {navController.navigate("createCompany")})

                }
                Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(Color.Gray))
            }
        }
    }

}

@Preview
@Composable
fun LogInPreview(){
    LogInCompose(navController = rememberNavController())
}