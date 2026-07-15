package com.example.cleveralarmclock.presentation.manageAlarmFeature.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleveralarmclock.core.ui.WheelPicker
import com.example.cleveralarmclock.presentation.manageAlarmFeature.SettingsAlarmViewModel

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,    viewModel: SettingsAlarmViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    val hoursList = remember(uiState.is24Hours) {
        val size = if (uiState.is24Hours) 24 else 12
        val offset = if (uiState.is24Hours) 0 else 1
        List(size) { index -> "%02d".format(index + offset) }
    }
    val minutesList = remember { List(60) { index -> "%02d".format(index) } }
    val amPmList = remember { listOf("AM", "PM") }

    Column(
        modifier = modifier
            .widthIn(max = 400.dp)
            .padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = "Hours",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = "Minutes",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            if (!uiState.is24Hours) {
                Text(
                    text = "Format",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray.copy(alpha = 0.15f))
            )

            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                // HOUR COLUMN
                Column (
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
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
                            onItemSelected = { amPm -> viewModel.onAmPmChange(amPm) },
                            isInfinite = false
                        )
                    }
                }
            }
        }
    }
}
