package com.example.workflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ui.customCompose.FilledButton
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.customCompose.UserTextField
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlCreateTaskCompose(navController:NavController){
    val coroutineScope = rememberCoroutineScope()

    var taskName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    WorkFlowTheme {

        Scaffold(
            topBar = { TopBarControl(onClick = { navController.popBackStack() }, visible = true) }
        ) { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 10.dp)
                ) {

                    Text(
                        text = "Crear Tarea",
                        modifier = Modifier,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 35.sp
                        )
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(25.dp, 0.dp, 20.dp, 25.dp)
                            .fillMaxWidth()
                    ) {
                        UserTextField(
                            text = "Nombre de la tarea",
                            taskName,
                            onTextValueChange = { taskName = it })
                        OutlinedTextField(
                            label = { Text(text = "Descripción de la tarea") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            value = description,
                            onValueChange = { description = it }
                        )

                        val datePickerState = rememberDatePickerState()
                        val timePickerState = rememberTimePickerState()
                        val showDialogDate = rememberSaveable { mutableStateOf(false) }
                        val showDialogTime = rememberSaveable { mutableStateOf(false) }
                        val startOrEnd = rememberSaveable { mutableStateOf(true) }
                        var startDate by remember { mutableLongStateOf(0) }
                        var endDate by remember { mutableLongStateOf(0) }
                        var startTimeHour by remember { mutableIntStateOf(0) }
                        var endTimeHour by remember { mutableIntStateOf(0) }
                        var startTimeMinutes by remember { mutableIntStateOf(0) }
                        var endTimeMinutes by remember { mutableIntStateOf(0) }
                        var startDateString = rememberSaveable { mutableStateOf("Seleccionar fecha") }
                        var endDateString = rememberSaveable { mutableStateOf("Seleccionar fecha") }
                        var startTimeString = rememberSaveable { mutableStateOf("Seleccionar hora") }
                        var endTimeString = rememberSaveable { mutableStateOf("Seleccionar hora") }

                        val format = SimpleDateFormat("dd/MM/yyyy")

                        Text("Hora y fecha de comienzo")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            FilledButton(
                                onClick = {
                                    startOrEnd.value = true
                                    showDialogTime.value = true
                                },
                                text = startTimeString.value,
                                modifier = Modifier,
                                containerColor = BlueWorkFlow,
                                contentColor = Color.White
                            )

                            FilledButton(
                                onClick = {
                                    startOrEnd.value = true
                                    showDialogDate.value = true
                                },
                                text = startDateString.value,
                                modifier = Modifier,
                                containerColor = BlueWorkFlow,
                                contentColor = Color.White
                            )
                        }

                        Text("Hora y fecha de finalización")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilledButton(
                                onClick = {
                                    startOrEnd.value = false
                                    showDialogTime.value = true
                                },
                                text = endTimeString.value,
                                modifier = Modifier,
                                containerColor = BlueWorkFlow,
                                contentColor = Color.White
                            )
                            FilledButton(
                                onClick = {
                                    startOrEnd.value = false
                                    showDialogDate.value = true
                                },
                                text = endDateString.value,
                                modifier = Modifier,
                                containerColor = BlueWorkFlow,
                                contentColor = Color.White
                            )
                        }

                        if (showDialogTime.value) {
                            DatePickerDialog(
                                onDismissRequest = { showDialogTime.value = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        showDialogTime.value = false
                                        if (startOrEnd.value) {
                                            startTimeHour = timePickerState.hour!!
                                            startTimeMinutes = timePickerState.minute!!
                                            startTimeString.value = "$startTimeHour:$startTimeMinutes"
                                        } else {
                                            endTimeHour = timePickerState.hour!!
                                            endTimeMinutes = timePickerState.minute!!
                                            endTimeString.value = "$endTimeHour:$endTimeMinutes"
                                        }
                                    }) {
                                        Text("Confirmar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialogTime.value = false }) {
                                        Text("Cancelar")
                                    }
                                }
                            ) {
                                TimePicker(state = timePickerState)
                            }
                        }

                        if (showDialogDate.value) {
                            DatePickerDialog(
                                onDismissRequest = { showDialogDate.value = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        showDialogDate.value = false
                                        if (startOrEnd.value) {
                                            startDate = datePickerState.selectedDateMillis!!
                                            startDateString.value = format.format(startDate)
                                        } else {
                                            endDate = datePickerState.selectedDateMillis!!
                                            endDateString.value = format.format(endDate)
                                        }
                                    }) {
                                        Text("Confirmar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialogDate.value = false }) {
                                        Text("Cancelar")
                                    }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }
/*
                        LazyColumn(
                            modifier = Modifier
                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                .border(
                                    1.dp, Color.Gray,
                                    RoundedCornerShape(5)
                                )
                                .height(200.dp)


                        ) {
                            items(10) {
                                EmployeeTaskRow()
                            }
                        }
*/
                        FilledButton(
                            onClick = {
                                val dateStart = Date(startDate + (startTimeHour*60*60*1000)+ (startTimeMinutes*60*1000))
                                val dateEnd = Date(endDate + (endTimeHour*60*60*1000)+ (endTimeMinutes*60*1000))
                                val task =  Task(
                                    name = TaskName(taskName),
                                    description = TaskDescription(description),
                                    startHour = StartHour(dateStart),
                                    endHour = EndHour(dateEnd),
                                    done = false
                                )
                                coroutineScope.launch {
                                    App.instance.taskService.createTask(task)
                                    navController.popBackStack()
                                }


                                

                            }, text = "Crear tarea",
                            Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(), GreenWorkFlow, Color.Black
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun EmployeeTaskRow(){

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ){
            Text("UserName")
        }
    }
}

@Preview
@Composable
fun ControlCreateTaskPreview(){
    ControlCreateTaskCompose(navController = rememberNavController())
}