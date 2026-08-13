package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleveralarmclock.core.domain.task.TaskType
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.alarmRingFeature.AlarmRingScreen
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature.CameraTaskScreen
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature.ShakeTaskScreen

sealed class AlarmAlertScreens(val route: String){
    data object AlarmRing: AlarmAlertScreens("alarmRing/{alarmId}")
    data object CameraTask: AlarmAlertScreens("cameraTask")
    data object ShakeTask: AlarmAlertScreens("shakeTask")
}



@Composable
fun AlarmNavHost(
    alarmId: Int,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = AlarmAlertScreens.AlarmRing.route
    ){
        composable(
            AlarmAlertScreens.AlarmRing.route,
            arguments = listOf(
                navArgument("alarmId") {
                    type = NavType.IntType
                    defaultValue = alarmId
                }
            )
        ){
            AlarmRingScreen(
                onStopAlarm = {taskId ->

                    val targetRoute = when(taskId){
                        TaskType.SHAKE -> AlarmAlertScreens.ShakeTask.route
                        TaskType.CAMERA -> AlarmAlertScreens.CameraTask.route
                    }

                    navController.navigate(targetRoute){
                        popUpTo(AlarmAlertScreens.AlarmRing.route){inclusive = true}
                    }
                }
            )
        }

        composable(
            AlarmAlertScreens.CameraTask.route,
            arguments = listOf(
                navArgument("alarmId") {
                    type = NavType.IntType
                    defaultValue = alarmId
                }
            )

        ){
            CameraTaskScreen()
        }

        composable(
            AlarmAlertScreens.ShakeTask.route,
            arguments = listOf(
                navArgument("alarmId") {
                    type = NavType.IntType
                    defaultValue = alarmId
                }
            )
        ){
            ShakeTaskScreen()
        }
    }
}