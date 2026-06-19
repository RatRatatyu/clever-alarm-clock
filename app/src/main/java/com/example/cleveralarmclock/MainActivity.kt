package com.example.cleveralarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.cleveralarmclock.alarmHandler.AlarmReceiver
import com.example.cleveralarmclock.ui.theme.CleverAlarmClockTheme
import java.util.Calendar
import android.Manifest
import com.example.cleveralarmclock.alarmHandler.AlarmSchedule


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFormAlarm = intent.getBooleanExtra("ALARM_TRIGGERED", false)

        enableEdgeToEdge()
        setContent {

            // Твоя обычная логика экранов
            if (isFormAlarm) {
                ScreenForAlarm()
            } else {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current;
    val sch = AlarmSchedule(context = context);
    Scaffold (
        modifier = modifier.fillMaxSize()
    ){ innerPading->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPading)
        ){
            Button(onClick ={ sch.schedule() }) { Text("Start alarm clokcl")}
            Button(onClick ={ sch.cancel() }) { Text("Cancle alarm clokcl")}

        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenForAlarm(modifier: Modifier = Modifier) {
    Scaffold(
        Modifier.fillMaxSize(),
        {
            TopAppBar(
                title = { Text("hi") }
            )
        }

    ) { innerPadding ->

        Column (
            modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Box(
                modifier
                    .height(100.dp)
                    .width(100.dp)
                    .background(color = Color.Blue),
                contentAlignment = Alignment.Center
            ){
               Text("Alarm Clockl", style = MaterialTheme.typography.titleLarge)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleverAlarmClockTheme {
        MainScreen()
    }
}