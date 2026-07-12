package com.example.cleveralarmclock.presentation.manageAlarmFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.core.ui.WheelPicker

@Composable
fun SettingsAlarm(modifier: Modifier = Modifier) {
    Scaffold (
        modifier = modifier
            .fillMaxSize()
    ){ innerPadding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)


            ){
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 20.dp),
                ){
                    // HOUR COLUMN
                    Column (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text("Hours", style = MaterialTheme.typography.titleSmall)
                        WheelPicker()
                    }

                    // MINUTE COLUMN
                    Column (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text("Minutes", style = MaterialTheme.typography.titleSmall)
                        WheelPicker()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(color = Color.Red)


            ){

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