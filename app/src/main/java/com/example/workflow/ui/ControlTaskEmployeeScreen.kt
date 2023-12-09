package com.example.workflow.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workflow.domain.entities.EndHour
import com.example.workflow.domain.entities.StartHour
import com.example.workflow.domain.entities.Task
import com.example.workflow.domain.entities.TaskDescription
import com.example.workflow.domain.entities.TaskName
import com.example.workflow.ui.customCompose.BottomBar
import com.example.workflow.ui.customCompose.TopBar
import com.example.workflow.ui.customCompose.TopBarControl
import com.example.workflow.ui.theme.BlueWorkFlow
import com.example.workflow.ui.theme.GreenWorkFlow
import com.example.workflow.ui.theme.WorkFlowTheme
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
fun ControlTaskEmployeeCompose(navController: NavController) {

    val dataSource = CalendarDataSource()
    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    WorkFlowTheme {

        Scaffold(
            topBar = { TopBarControl({},true) },
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
            }, floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(WorkFlowScreen.ControlAddEmployee.name) }, containerColor = GreenWorkFlow) {
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



