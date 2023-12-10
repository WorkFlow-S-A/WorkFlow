package com.example.workflow.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.R
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.ui.customCompose.CustomClickableText
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.PasswordTextField
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.ui.theme.GreenWorkFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LogInCompose(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()

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
                    UserTextField(text = "Email",userName, onTextValueChange = { userName = it})
                    PasswordTextField(text="Contraseña",userPassword, onPasswordValueChange = {userPassword = it})
                    val context = LocalContext.current
                    FilledButton(onClick =
                    {
                        coroutineScope.launch{
                            val uid : String = App.instance.firebaseAuthentication.logIn(userName, userPassword)
                            if(uid != "" ){
                                Log.d("ES ADMIN", "ESTA ENTRANDO")
                                if(CompanyFirebaseRepository.findCompany(uid)){
                                    Log.d("ES ADMIN", "ES ADMIN")
                                    navController.navigate("controlEmployees")
                                } else{
                                    Log.d("NO ES ADMIN", "NO ES ADMIN")
                                    App.instance.currentEmployeeUid = uid
                                    navController.navigate("scheduleControlEmployee")
                                }

                            } else {
                                // Las credenciales son incorrectas, mostrar un mensaje al usuario para volver a intentarlo
                                Toast.makeText(
                                    context
                                    ,"Credenciales incorrectas, vuelve a intentarlo",
                                    Toast.LENGTH_LONG).show()
                            }

                        }
                    }, text = "ENTRAR",
                        Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),Color.Red,Color.White)
                    CustomClickableText(text = "¿Olvidaste la contraseña?", modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 10.dp), onClick = {navController.navigate("forgotPassword")})
                    CustomClickableText(text = "Crear una empresa", modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 10.dp), onClick = {navController.navigate("createCompany")})



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

