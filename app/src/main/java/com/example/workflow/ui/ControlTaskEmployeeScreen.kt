package com.example.workflow.ui

import android.os.Build
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.ui.customCompose.CustomClickableText
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ControlTaskEmployeeCompose(navController: NavController) {

    val dataSource = CalendarDataSource()
    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments
    val employeeId = arguments?.getString("employeeId")
    var companyName by remember{ mutableStateOf("Company") }
    val coroutineScope = rememberCoroutineScope()


    var email by remember { mutableStateOf("jpereiro1@gmail.com") }
    var name by remember { mutableStateOf("User name") }
    var uid by remember { mutableStateOf("12345678X") }
    var workHours by remember { mutableIntStateOf(35) }
    var workedHours by remember { mutableIntStateOf(35) }

    if(employeeId != null){
        LaunchedEffect(Unit) {
            val employee = App.instance.employeeService.getEmployee(employeeId)
            email = employee.email.email
            name = employee.name.name
            uid = employee.employeeId.id
            workHours = employee.workHours.workHours
            workedHours = employee.workedHours.workedHours
            companyName = CompanyFirebaseRepository.getCurrentCompanyName()

        }
    }

    WorkFlowTheme {

        Scaffold(
           // topBar = { TopBarControl({},true) },
            content = {padding ->
                Column (modifier = Modifier.padding(padding),verticalArrangement = Arrangement.spacedBy(16.dp)){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomClickableText(companyName,Modifier , onClick = {navController.popBackStack()} )
                            Text(name)
                        }
                        HeaderTask(
                            calendarUiModel,
                            onPrevClickListener = { startDate ->
                                // refresh the CalendarUiModel with new data
                                // by get data with new Start Date (which is the startDate-1 from the visibleDates)
                                val finalStartDate = startDate.minusDays(1)
                                calendarUiModel = dataSource.getData(
                                    startDate = finalStartDate,
                                    lastSelectedDate = calendarUiModel.selectedDate.date
                                )
                            },
                            onNextClickListener = { endDate ->
                                // refresh the CalendarUiModel with new data
                                // by get data with new Start Date (which is the endDate+2 from the visibleDates)
                                val finalStartDate = endDate.plusDays(2)
                                calendarUiModel = dataSource.getData(
                                    startDate = finalStartDate,
                                    lastSelectedDate = calendarUiModel.selectedDate.date
                                )
                            }
                        )
                        Content(
                            calendarUiModel,
                            onDateClickListener = { date ->
                                // refresh the CalendarUiModel with new data
                                // by changing only the `selectedDate` with the date selected by User
                                calendarUiModel = calendarUiModel.copy(
                                    selectedDate = date,
                                    visibleDates = calendarUiModel.visibleDates.map {
                                        it.copy(
                                            isSelected = it.date.isEqual(date.date)
                                        )
                                    }
                                )
                            }
                        )
                        LazyColumn(){

                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val tasks = App.instance.taskService.getAllTasks()
                                    items(items = tasks) { task ->
                                        /*TaskRow(task = Task(id = UUID.randomUUID(), TaskName("juan"),
                                            TaskDescription("fasdfasdf"), StartHour(startHour = Date(System.currentTimeMillis()+10000)),EndHour(endHour = Date(System.currentTimeMillis()+10000)),done = true)
                                        )*/
                                        TaskRow(task)
                                    }
                                } catch (e: Exception) {

                                }
                            }
                        }
                    }
                }
            }, floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(WorkFlowScreen.ControlAddTask.name + "/$employeeId") }, containerColor = GreenWorkFlow) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        )
    }
}









@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ControlTaskEmployeePreview() {
    ControlTaskEmployeeCompose(navController = rememberNavController())

}



