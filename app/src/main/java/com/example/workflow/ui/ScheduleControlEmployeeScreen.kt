package com.example.workflow.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import com.example.workflow.utils.bluetooth.BluetoothServiceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleControlEmployeeCompose(navController: NavController) {

    WorkFlowTheme {
        Scaffold(
            topBar = { TopBar() },
            content = {padding ->
                Column(modifier= Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Control horario", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(LocalDate.now().toString(), style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
                        Button(onClick = {
                            val bluetoothService = BluetoothServiceHolder.bluetoothService
                            bluetoothService?.startDiscovery()
                            if (bluetoothService?.connectToDevice() == true) {
                                Log.d("CheckIn", "Se ha encontrado el dispositivo")
                                CoroutineScope(Dispatchers.IO).launch {
                                    val employee = App.instance.currentEmployeeUid?.let {
                                        App.instance.employeeService.getEmployee(
                                            it
                                        )
                                    }
                                    if (employee != null) {
                                        App.instance.employeeService.checkIn(employee, Date().toString())
                                    }
                                }
                            } else Log.d("CheckIn", "No se ha encontrado el dispositivo")
                        }, colors = ButtonDefaults.buttonColors(containerColor = BlueWorkFlow, contentColor = Color.White), shape = ShapeDefaults.Small) {
                            Text(text = "Marcar entrada" )
                        }
                        Button(onClick = {
                            val bluetoothService = BluetoothServiceHolder.bluetoothService
                            bluetoothService?.startDiscovery()
                            if (bluetoothService?.connectToDevice() == true) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val employee = App.instance.currentEmployeeUid?.let {
                                        App.instance.employeeService.getEmployee(
                                            it
                                        )
                                    }
                                    if (employee != null) {
                                        App.instance.employeeService.checkOut(employee, Date().toString())
                                    }
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = BlueWorkFlow, contentColor = Color.White), shape = ShapeDefaults.Small) {
                            Text(text = "Marcar salida" )
                        }
                    }

                    LazyColumn (modifier = Modifier
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                        .fillMaxSize()){
                        //LLAMAR A LA BASE DE DATOS Y OBTENER LAS TAREAS DE ESE DIA PARA MOSTRARLO AQUI
                        items(5) {
                            HourRow(
                                Date(System.currentTimeMillis()+10000),
                                Date(System.currentTimeMillis()+1000000),
                            )
                        }
                    }

                }
            },
            bottomBar = { BottomBar(navController) }
            , floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    containerColor = GreenWorkFlow

                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera QR")
            } }
        )

    }
}

@Composable
fun HourRow(hourStart: Date,hourEnd: Date){

    val formatTitle = SimpleDateFormat("dd/MM/yyyy")
    val formatHour = SimpleDateFormat("HH:mm")

    Row(modifier = Modifier
        .padding(bottom = 8.dp)
        .background(BlueWorkFlow)
        .fillMaxSize()
        .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(formatTitle.format(hourStart), color= Color.White)
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Entrada", color = Color.White)
                Text(formatHour.format(hourStart), color = GreenWorkFlow)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Salida", color = Color.White)
                Text(formatHour.format(hourEnd), color = GreenWorkFlow)
            }
        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ScheduleControlEmployeePreview(){
    ScheduleControlEmployeeCompose(navController = rememberNavController())
}