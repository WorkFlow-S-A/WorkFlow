        @file:OptIn(ExperimentalMaterial3Api::class)

package com.example.workflow.ui.customCompose

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.ui.WorkFlowScreen
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.jua

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTextField(text: String, userText:String,onTextValueChange: (String) -> Unit){

    OutlinedTextField(
        value = userText,
        onValueChange = {onTextValueChange(it)},
        label = { Text(text = text) },
        maxLines = 1,
        modifier = Modifier.fillMaxWidth())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(text: String, passwordValue:String,onPasswordValueChange: (String) -> Unit){

    OutlinedTextField(
        value = passwordValue,
        onValueChange = {onPasswordValueChange(it)},
        label = { Text(text = text)},
        visualTransformation = PasswordVisualTransformation(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(text: String, emailValue:String,onEmailValueChange: (String) -> Unit){
    OutlinedTextField(
        value = emailValue,
        onValueChange = {onEmailValueChange(it)},
        label = { Text(text = text)},
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomClickableText(text:String,modifier: Modifier,onClick: () -> Unit){
    ClickableText(
        text = AnnotatedString(text,
            spanStyle = SpanStyle(
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline
            ),
        ),
        modifier = modifier,
        onClick = {onClick()}
    )
}

@Composable
fun FilledButton(onClick: () -> Unit, text: String, modifier: Modifier){
    Button(onClick = { onClick() }, modifier = modifier, colors = ButtonDefaults.buttonColors(containerColor = GreenWorkFlow, contentColor = Color.Black), shape = ShapeDefaults.Small) {
        Text(text = text )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BlueWorkFlow),
        title = {
            Text(
                style = TextStyle(
                    color = Color.White,
                    fontFamily = jua,
                    fontSize = 25.sp
                ), text = "WORKFLOW"
            )
        }
    )
}


@Composable
fun BottomBar(navController: NavController){
    BottomAppBar(
        containerColor = BlueWorkFlow,
        contentColor = Color.White,
    ) {
        Row(modifier= Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
            NavigationItem(navController = navController, route = WorkFlowScreen.TaskEmployee.name, icon = Icons.Default.Task, label = "Tareas")
            NavigationItem(navController = navController, route = WorkFlowScreen.ScheduleControlEmployee.name, icon = Icons.Default.Work, label = "Marcar")
            NavigationItem(navController = navController, route = WorkFlowScreen.Profile.name, icon = Icons.Default.Person, label = "Perfil")
        }

    }
}



@Composable
fun NavigationItem(navController: NavController,route:String, icon: ImageVector,label:String){
    val isCurrentDestination = navController.currentDestination?.route == route

    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isCurrentDestination) Color.White else BlueWorkFlow),
        onClick = { if (!isCurrentDestination) navController.navigate(route) }) {

        Icon(tint = if(isCurrentDestination) BlueWorkFlow else Color.White,imageVector = icon, contentDescription = label)
    }


}




/*,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
            )
        }*/





