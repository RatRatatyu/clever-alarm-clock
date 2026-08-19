package com.example.cleveralarmclock.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleveralarmclock.presentation.mainScreenFeature.MainScreen
import com.example.cleveralarmclock.presentation.manageAlarmFeature.SettingsAlarm

sealed class AppScreens(val route: String) {
    data object Home : AppScreens("home")
    data object AlarmSettings : AppScreens("alarmSettings/{alarmId}") {
        fun passId(alarmId: Int): String {
            return "alarmSettings/$alarmId"
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass
){
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route
    ){
        composable(AppScreens.Home.route){
            MainScreen(
                onAddAlarmClick = { alarmId ->
                    navController.navigate(AppScreens.AlarmSettings.passId(alarmId))
                },
                windowSizeClass = windowSizeClass
            )
        }
        if(isCompact){
            composable(
                route = AppScreens.AlarmSettings.route,
                arguments= listOf(
                    navArgument("alarmId"){type = NavType.IntType}
                )
            ){
                SettingsAlarm(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    windowSizeClass = windowSizeClass
                )
            }
        }else{
            dialog(
                route = AppScreens.AlarmSettings.route,
                arguments = listOf(
                    navArgument("alarmId"){type = NavType.IntType}
                ),
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
            ){
                SettingsAlarm(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    windowSizeClass = windowSizeClass
                )
            }
        }
    }
}

