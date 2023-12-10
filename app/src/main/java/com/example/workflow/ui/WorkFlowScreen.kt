package com.example.workflow.ui

import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workflow.R


enum class WorkFlowScreen(@StringRes val title:Int){
    Start(title = R.string.app_name),
    Home(title = R.string.home),
    LogIn(title = R.string.logIn),
    ForgotPassword(title = R.string.forgotPassword),
    CreateCompany(title= R.string.createCompany),
    TaskEmployee(title = R.string.taskEmployee),
    Profile(title = R.string.profile),
    ScheduleControlEmployee(title = R.string.scheduleControlEmployee),
    ControlEmployees(title = R.string.controlEmployees),
    ControlEmployeesProfile(title = R.string.controlEmployeesProfile),
    ControlAddEmployee(title = R.string.controlAddEmployee),
    ControlTaskEmployee(title = R.string.controlTaskEmployee),
    ControlCreateTask(title = R.string.controlCreateTask),
    ControlAddTask(title=R.string.controlAddTask)
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkFlowApp(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = WorkFlowScreen.Start.name){
        composable(route = WorkFlowScreen.Start.name){
            StartCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.LogIn.name){
            LogInCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.ForgotPassword.name){
            ForgotPasswordCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.CreateCompany.name){
            CreateCompanyCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.TaskEmployee.name){
            TaskEmployeeCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.Profile.name){
            ProfileCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.ScheduleControlEmployee.name){
            ScheduleControlEmployeeCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.ControlEmployees.name){
            ControlEmployeesCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.ControlEmployeesProfile.name){
            ControlProfileCompose(navController = navController)
        }
        
        composable(route = WorkFlowScreen.ControlAddEmployee.name){
            AddEmployeeCompose(navController = navController)
        }


        composable(
            route = WorkFlowScreen.ControlEmployeesProfile.name+"/{employeeId}",
            arguments = listOf(navArgument("employeeId"){type = NavType.StringType})
        ){
            ControlProfileCompose(navController = navController)
        }

        composable(
            route = WorkFlowScreen.ControlTaskEmployee.name+"/{employeeId}",
            arguments = listOf(
                navArgument("employeeId"){type = NavType.StringType})
        ){
            ControlTaskEmployeeCompose(navController = navController)
        }

        composable(route = WorkFlowScreen.ControlCreateTask.name){
            ControlCreateTaskCompose(navController = navController)
        }

        composable(
            route = WorkFlowScreen.ControlAddTask.name+"/{employeeId}",
            arguments = listOf(
                navArgument("employeeId"){type = NavType.StringType})
        ){
            ControlAddTaskCompose(navController = navController)
        }
        
        



    }
}
