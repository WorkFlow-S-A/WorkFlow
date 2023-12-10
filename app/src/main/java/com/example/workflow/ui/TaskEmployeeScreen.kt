package com.example.workflow.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.App
import com.example.workflow.adapters.repositories.firebase.CompanyFirebaseRepository
import com.example.workflow.domain.entities.Email
import com.example.workflow.domain.entities.Employee
import com.example.workflow.domain.entities.EmployeeID
import com.example.workflow.domain.entities.EmployeeName
import com.example.workflow.domain.entities.EmployeeSurname
import com.example.workflow.domain.entities.EmployeeWorkHours
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskEmployeeCompose(navController: NavController) {



    val dataSource = CalendarDataSource()
    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    WorkFlowTheme {

        Scaffold(
            topBar = { TopBar() },
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
                            Text("Empresa")
                            Text("Usuario")
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
                    }
                }
            },
            bottomBar = {BottomBar(navController)}
        )
    }
}

data class CalendarUiModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
){
    val startDate: Date = visibleDates.first() // the first of the visible dates
    val endDate: Date = visibleDates.last() // the last of the visible dates

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        @RequiresApi(Build.VERSION_CODES.O)
        val day: String = date.format(DateTimeFormatter.ofPattern("E")) // get the day by formatting the date
    }

}

class CalendarDataSource {

    val today: LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            return LocalDate.now()
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderTask(data: CalendarUiModel, // calbacks to click previous & back button should be registered outside
               onPrevClickListener: (LocalDate) -> Unit,
               onNextClickListener: (LocalDate) -> Unit,){
    Row (horizontalArrangement = Arrangement.SpaceBetween){
        Text(
            // show "Today" if user selects today's date
            // else, show the full format of the date
            text = "Fecha: "+ data.selectedDate.date.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                ),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        Row {
            IconButton(onClick = { onPrevClickListener(data.startDate.date) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "Previous"
                )
            }
            IconButton(onClick = { onNextClickListener(data.endDate.date) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Next"
                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TaskEmployeeComposePreview() {
    TaskEmployeeCompose(navController = rememberNavController())

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(data: CalendarUiModel, onDateClickListener: (CalendarUiModel.Date) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    Column {

        LazyRow {
            items(items = data.visibleDates) { date ->
                ContentItem(date, onDateClickListener)
            }
        }

        LazyColumn (modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp)){
            //LLAMAR A LA BASE DE DATOS Y OBTENER LAS TAREAS DE ESE DIA PARA MOSTRARLO AQUI
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

@Composable
fun TaskRow(task : Task){

    val checked = remember { mutableStateOf(true) }

    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.done) {
                BlueWorkFlow
            } else {
                Color.LightGray
            }
        )){
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(task.name.toString())
            Row (verticalAlignment = Alignment.CenterVertically){
                Column() {
                    //val dateFormater : DateTimeFormatter
                    Text(task.startHour.toString(), style = MaterialTheme.typography.labelSmall)
                    Text(task.endHour.toString() ,style = MaterialTheme.typography.labelSmall)
                }
                //Llamar a la db para cambiar el done de la tarea
                Checkbox(checked = true, onCheckedChange = {checked.value = it})
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentItem(date: CalendarUiModel.Date, onClickListener: (CalendarUiModel.Date) -> Unit,){
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { // making the element clickable, by adding 'clickable' modifier
                onClickListener(date)
            },
        colors = CardDefaults.cardColors(
            // background colors of the selected date
            // and the non-selected date are different
            containerColor = if (date.isSelected) {
                BlueWorkFlow
            } else {
                Color.LightGray
            }
        )
    ){
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ){
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
