package com.example.cleveralarmclock.presentation.manageAlarmFeature


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleveralarmclock.core.ui.WheelPicker

@Composable
fun SettingsAlarm(
    modifier: Modifier = Modifier,
    viewModel: SettingsAlarmViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
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
                    text = "${uiState.selectedHours} : ${uiState.selectedMinutes}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Button(onClick = {}) { Text("add")}
        }
    }
}



@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    viewModel: SettingsAlarmViewModel = hiltViewModel()
){

    val uiState by viewModel.uiState.collectAsState()

    val hoursList = remember(uiState.is24Hours) {
        if (uiState.is24Hours) {
            List(24) { index -> "%02d".format(index) }
        } else {
            List(12) { index -> "%02d".format(index + 1) }
        }
    }
    val minutesList = remember { List(60) { index -> "%02d".format(index) } }
    val amPmList = remember { listOf("AM", "PM") }

    Box (
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = if (uiState.is24Hours) 260.dp else 340.dp),
        contentAlignment = Alignment.Center,

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray.copy(alpha = 0.15f))
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp)
                .padding(all = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            // HOUR COLUMN
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Hours", style = MaterialTheme.typography.titleSmall)
                WheelPicker(
                    items = hoursList,
                    initialItem = uiState.selectedHours,
                    onItemSelected = { hours -> viewModel.onHoursChange(hours) }
                )
            }

            // MINUTE COLUMN
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Minutes", style = MaterialTheme.typography.titleSmall)
                WheelPicker(
                    items = minutesList,
                    initialItem = uiState.selectedMinutes,
                    onItemSelected = { minutes -> viewModel.onMinutesChange(minutes) }
                )
            }

            if(!uiState.is24Hours){

                // AM PM COLUMN
                Column (
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    WheelPicker(
                        items = amPmList,
                        initialItem = uiState.selectedAmPm,
                        onItemSelected = { amPm -> viewModel.onAmPmChange(amPm) }
                    )
                }
            }

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