package com.example.cleveralarmclock.presentation.manageAlarmFeature

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.DayOfWeekPicker
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.WheelTimePicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAlarm(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: SettingsAlarmViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
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
                if(uiState.isLoading){
                    CircularProgressIndicator()
                }else {
                    WheelTimePicker(
                        is24Hours = uiState.is24Hours,
                        selectedHours = uiState.selectedHours,
                        selectedMinutes = uiState.selectedMinutes,
                        selectedAmPm = uiState.selectedAmPm,
                        onHoursChange = { hours -> viewModel.onHoursChange(hours) },
                        onMinutesChange = { minutes -> viewModel.onMinutesChange(minutes) },
                        onAmPmChange = { amPm -> viewModel.onAmPmChange(amPm) }
                    )
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .background(color = MaterialTheme.colorScheme.background)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                DayOfWeekPicker(
                    isAlDaysSelected =uiState.isAllDaysSelected,
                    selectedDayOfWeek =  uiState.selectedDayOfWeek,
                    selectAllDayOfWeek = { viewModel.selectAllDayOfWeek() },
                    onSelectDayOfWeek = {day -> viewModel.onSelectDayOfWeek(day)}
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .weight(3f),
                contentAlignment = Alignment.Center
            ){
                Button({
                    viewModel.onAddAlarmClock()
                    onBackClick()
                }) {Text("add") }
            }


        }
    }
}
