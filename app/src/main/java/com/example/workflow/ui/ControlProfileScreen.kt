package com.example.workflow.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TextFieldCustom
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlProfileCompose(navController: NavController) {


    WorkFlowTheme {
        Scaffold(
            topBar = { TopBarControl({
                navController.popBackStack()
                },true) },
            content = {padding ->
                Column(modifier= Modifier.padding(padding)) {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(152, 177, 255))) {
                        /*IconButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = {}
                        ) {
                            Icon(tint = Color.Black, imageVector = Icons.Default.Edit, contentDescription = "Boton para editar la foto de perfil")
                        }*/
                        Image(
                            modifier = Modifier
                                .width(150.dp)
                                .align(Alignment.CenterHorizontally)
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


                        Row(horizontalArrangement = Arrangement.SpaceAround, modifier= Modifier.fillMaxWidth()){

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                                Text("Horas estimadas")
                                Box(
                                    modifier = Modifier
                                        .background(Color(152, 177, 255))
                                        .clip(RoundedCornerShape(10.dp)),
                                    Alignment.Center
                                ){
                                    //Poner las horas
                                    Text("35h",modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .padding(15.dp))
                                }
                            }

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                                Text("Horas realizadas")
                                Box(
                                    modifier = Modifier
                                        .background(Color(152, 177, 255)),

                                    Alignment.Center
                                ){
                                    //Poner las horas
                                    Text("35h",modifier = Modifier
                                        .padding(15.dp)
                                        .clip(RoundedCornerShape(50)))
                                }
                            }
                        }


                        Column(){
                            FilledButton(
                                onClick = { /*TODO*/ },
                                text = "Tareas",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                GreenWorkFlow,
                                Color.Black
                            )
                            FilledButton(
                                onClick = { /*TODO*/ },
                                text = "Eliminar empleado",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                Color.Red,
                                Color.White
                            )
                        }

                        Text(text = LocalContext.current.getString(R.string.version_app), style = TextStyle(
                            Color.Gray)
                        )



                    }
                }
            },
            bottomBar = {  }
        )
    }

}

@Preview
@Composable
fun ControlProfilePreview(){
    ControlProfileCompose(navController = rememberNavController())
}