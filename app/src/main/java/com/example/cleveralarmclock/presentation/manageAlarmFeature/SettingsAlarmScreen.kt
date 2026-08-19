package com.example.cleveralarmclock.presentation.manageAlarmFeature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.domain.task.AlarmTask
import com.example.cleveralarmclock.core.domain.task.AlarmTaskProvider
import com.example.cleveralarmclock.core.domain.task.TaskType
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.AlarmTaskPicker
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.DayOfWeekPicker
import com.example.cleveralarmclock.presentation.manageAlarmFeature.components.WheelTimePicker
import java.time.DayOfWeek


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAlarm(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: SettingsAlarmViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val alarmTaskList = viewModel.alarmTaskList

    when(windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> SettingsAlarmCompact(
            modifier = modifier,
            uiState = uiState,
            alarmTaskList = alarmTaskList,
            onBackClick = onBackClick,
            onHoursChange = {hour -> viewModel.onHoursChange(hour) },
            onMinutesChange = {minute -> viewModel.onMinutesChange(minute) },
            onAmPmChange = {amPm -> viewModel.onAmPmChange(amPm) },
            onTaskSelected = {taskId -> viewModel.onTaskSelected(taskId)},
            selectAllDayOfWeek = { viewModel.selectAllDayOfWeek() },
            onSelectDayOfWeek = {dayOfWeek -> viewModel.onSelectDayOfWeek(dayOfWeek)},
            onAddAlarmClock = { viewModel.onAddAlarmClock() }
        )
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> SettingsAlarmExpanded(
            modifier = modifier,
            onDismissRequest = onBackClick,
            uiState = uiState,
            alarmTaskList = alarmTaskList,
            onHoursChange = {hour -> viewModel.onHoursChange(hour) },
            onMinutesChange = {minute -> viewModel.onMinutesChange(minute) },
            onAmPmChange = {amPm -> viewModel.onAmPmChange(amPm) },
            onTaskSelected = {taskId -> viewModel.onTaskSelected(taskId)},
            selectAllDayOfWeek = { viewModel.selectAllDayOfWeek() },
            onSelectDayOfWeek = {dayOfWeek -> viewModel.onSelectDayOfWeek(dayOfWeek)},
            onAddAlarmClock = { viewModel.onAddAlarmClock() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAlarmExpanded(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    uiState: SettingAlarmState,
    alarmTaskList: List<AlarmTask>,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onAmPmChange: (String) -> Unit,
    onTaskSelected: (TaskType) -> Unit,
    selectAllDayOfWeek: () -> Unit,
    onSelectDayOfWeek: (DayOfWeek) -> Unit,
    onAddAlarmClock: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Bar in Dialog
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

                Box(modifier = Modifier.size(48.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 64.dp))
                    } else {
                        WheelTimePicker(
                            is24Hours = uiState.is24Hours,
                            selectedHours = uiState.selectedHours,
                            selectedMinutes = uiState.selectedMinutes,
                            selectedAmPm = uiState.selectedAmPm,
                            onHoursChange = onHoursChange,
                            onMinutesChange = onMinutesChange,
                            onAmPmChange = onAmPmChange
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1.5f),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DayOfWeekPicker(
                            isAlDaysSelected = uiState.isAllDaysSelected,
                            selectedDayOfWeek = uiState.selectedDayOfWeek,
                            selectAllDayOfWeek = selectAllDayOfWeek,
                            onSelectDayOfWeek = onSelectDayOfWeek
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        AlarmTaskPicker(
                            alarmTaskList = alarmTaskList,
                            selectedTask = uiState.selectedTask,
                            onTaskSelected = onTaskSelected
                        )
                    }

                    Button(
                        onClick = {
                            onAddAlarmClock()
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.save_new_alarm),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAlarmCompact(
    modifier: Modifier = Modifier,
    uiState: SettingAlarmState,
    alarmTaskList: List<AlarmTask>,
    onBackClick: () -> Unit,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onAmPmChange: (String) -> Unit,
    onTaskSelected: (TaskType) -> Unit,
    selectAllDayOfWeek: () -> Unit,
    onSelectDayOfWeek: (DayOfWeek) -> Unit,
    onAddAlarmClock: () -> Unit
){
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
                        onHoursChange = { hours -> onHoursChange(hours) },
                        onMinutesChange = { minutes -> onMinutesChange(minutes) },
                        onAmPmChange = { amPm -> onAmPmChange(amPm) }
                    )
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                DayOfWeekPicker(
                    isAlDaysSelected = uiState.isAllDaysSelected,
                    selectedDayOfWeek = uiState.selectedDayOfWeek,
                    selectAllDayOfWeek = { selectAllDayOfWeek() },
                    onSelectDayOfWeek = {day -> onSelectDayOfWeek(day)}
                )
            }
            Box(Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                AlarmTaskPicker(
                    alarmTaskList = alarmTaskList,
                    selectedTask = uiState.selectedTask,
                    onTaskSelected = {taskId -> onTaskSelected(taskId)}
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ){
                Button({
                    onAddAlarmClock()
                    onBackClick()
                }) {Text(stringResource(R.string.save_new_alarm)) }
            }


        }
    }
}

@Preview(showSystemUi = true, device = TABLET)
@Composable
fun ExpandedPreview(){
    MaterialTheme{
        SettingsAlarmExpanded(
            onDismissRequest = {},
            uiState = SettingAlarmState(
                selectedHours = 13,
                selectedMinutes = 9,
                selectedDayOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
            ),
            alarmTaskList = AlarmTaskProvider.allTasks,
            onHoursChange = {},
            onMinutesChange = {},
            onAmPmChange = {},
            onTaskSelected = {},
            selectAllDayOfWeek = {},
            onSelectDayOfWeek = {},
            onAddAlarmClock = {}
        )
    }
}


@Preview(showSystemUi = true, device = PHONE)
@Composable
fun SettingsAlarmPreview(){
    MaterialTheme{
        SettingsAlarmCompact(
            uiState = SettingAlarmState(
                selectedHours = 13,
                selectedMinutes = 9,
                selectedDayOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
            ),
            alarmTaskList = AlarmTaskProvider.allTasks,
            onBackClick = {},
            onHoursChange = {},
            onMinutesChange = {},
            onAmPmChange = {},
            onTaskSelected = {},
            selectAllDayOfWeek = {},
            onSelectDayOfWeek = {},
            onAddAlarmClock = {}
        )
    }
}