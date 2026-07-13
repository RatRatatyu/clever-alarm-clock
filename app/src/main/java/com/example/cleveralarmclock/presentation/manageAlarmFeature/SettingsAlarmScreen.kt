package com.example.cleveralarmclock.presentation.manageAlarmFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleveralarmclock.core.ui.WheelPicker
import com.example.cleveralarmclock.presentation.mainScreenFeature.data.MainViewModel

@Composable
fun SettingsAlarm(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {

    var selectedHours by remember { mutableStateOf("00") }
    var selectedMin by remember { mutableStateOf("00") }


    val hoursList = remember { List(24) { index -> "%02d".format(index) } }
    val minutesList = remember { List(60) { index -> "%02d".format(index) } }

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
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                ){
                    // HOUR COLUMN
                    Column (
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text("Hours", style = MaterialTheme.typography.titleSmall)
                        WheelPicker(
                            items = hoursList,
                            onItemSelected = { hours -> selectedHours = hours }
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
                            onItemSelected = { minutes -> selectedMin = minutes }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(color = Color.Red),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "$selectedHours : $selectedMin",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Button(onClick = {viewModel.startAlarm(selectedHours.toInt(), selectedMin.toInt())}) { Text("add")}
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