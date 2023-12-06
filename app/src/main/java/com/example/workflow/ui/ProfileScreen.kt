package com.example.workflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.R
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.EmailTextField
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TextFieldCustom
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCompose(navController: NavController) {


    WorkFlowTheme {
        Scaffold(
            topBar = { TopBar() },
            content = {padding ->
                Column(modifier= Modifier.padding(padding)) {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(152, 177, 255))) {
                        IconButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = {}
                        ) {
                            Icon(tint = Color.Black, imageVector = Icons.Default.Edit, contentDescription = "Boton para editar la foto de perfil")
                        }
                        Image(
                            modifier = Modifier
                                .width(150.dp)
                                .align(CenterHorizontally)
                                .padding(15.dp),
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null
                        )

                        Text("User Name",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 10.dp),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Column (modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Column(){
                            TextFieldCustom("Email","jpereiro1@gmail.com")
                            TextFieldCustom("DNI","12345678X")
                            TextFieldCustom("Teléfono","+34 612 612 612")
                        }


                        Row(horizontalArrangement = Arrangement.SpaceAround){
                            Column(){
                                Text("Horas estimadas")
                                Card(modifier = Modifier.size(60.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(152, 177, 255)
                                    )){
                                    //Poner las horas
                                    Text("35h")
                                }
                            }

                            Column(){
                                Text("Horas realizadas")
                                Card(modifier = Modifier
                                    .size(60.dp)
                                    ,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(152, 177, 255)
                                    )){
                                    //Poner las horas
                                    Text("35h")
                                }
                            }
                        }

                        FilledButton(onClick = { /*TODO*/ }, text = "Cambiar contraseña", modifier =Modifier.padding(10.dp))
                        Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(
                            Color.Gray)
                        )


                    }
                }
                      },
            bottomBar = { BottomBar(navController) }
        )
    }

}

@Preview
@Composable
fun ProfileComposePreview(){
    ProfileCompose(navController = rememberNavController())
}