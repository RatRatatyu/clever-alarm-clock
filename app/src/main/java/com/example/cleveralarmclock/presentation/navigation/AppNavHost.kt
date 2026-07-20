package com.example.cleveralarmclock.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleveralarmclock.presentation.mainScreenFeature.MainScreen
import com.example.cleveralarmclock.presentation.manageAlarmFeature.SettingsAlarm

sealed class AppScreens(val route: String){
    data object Home: AppScreens("home")
    data object AlarmSettings: AppScreens("alarmSettings")
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route
    ){
        composable(AppScreens.Home.route){
            MainScreen(
                onAddAlarmClick = {
                    navController.navigate(AppScreens.AlarmSettings.route)
                }
            )
        }
        composable(AppScreens.AlarmSettings.route){
            SettingsAlarm(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

