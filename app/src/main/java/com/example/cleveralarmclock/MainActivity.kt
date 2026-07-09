package com.example.cleveralarmclock

import android.Manifest
import android.content.Intent

import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleveralarmclock.core.service.permission.PermissionHandler
import com.example.cleveralarmclock.core.service.permission.PermissionTypes
import com.example.cleveralarmclock.presentation.mainScreenFeature.MainScreen
import com.example.cleveralarmclock.presentation.mainScreenFeature.data.MainViewModel
import com.example.cleveralarmclock.ui.theme.CleverAlarmClockTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFormAlarm = intent.getBooleanExtra("ALARM_TRIGGERED", false)

        enableEdgeToEdge()
        setContent {

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ){isGranted ->
                if(isGranted){

                }else{

                }

            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val hasPermission = permissionHandler.hasPermission(Manifest.permission.POST_NOTIFICATIONS)

                    if (hasPermission == PermissionTypes.DENIED) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            if (isFormAlarm) {
                ScreenForAlarm()
            } else {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenForAlarm( //for testing
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
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
                Button(onClick = { viewModel.stopAlarmService() }){Text("stop sound")}
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