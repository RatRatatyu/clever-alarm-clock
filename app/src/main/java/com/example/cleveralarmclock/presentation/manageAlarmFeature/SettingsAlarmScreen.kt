package com.example.cleveralarmclock.presentation.manageAlarmFeature


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.WheelTimePicker

@Composable
fun SettingsAlarm(
    modifier: Modifier = Modifier,
    viewModel: SettingsAlarmViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initializeTime(context)
    }

    Scaffold (
        modifier = modifier.fillMaxSize()
    ){ innerPadding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                contentAlignment = Alignment.Center
            ) {
                WheelTimePicker()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(color = Color.Red),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "${uiState.selectedHours} : ${uiState.selectedMinutes} ${uiState.selectedAmPm}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Button(onClick = {}) { Text("add")}
        }
    }
}


@Preview (showSystemUi = true)
@Composable
private fun SettingsAlarmPrev() {
    MaterialTheme {
        SettingsAlarm()
    }
}