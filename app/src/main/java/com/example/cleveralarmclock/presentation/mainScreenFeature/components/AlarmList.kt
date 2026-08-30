package com.example.cleveralarmclock.presentation.mainScreenFeature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cleveralarmclock.presentation.mainScreenFeature.AlarmUiModel


@Composable
fun AlarmList(
    modifier: Modifier = Modifier,
    scheduleList: List<AlarmUiModel>,
    isSelectedMode: Boolean,
    isChecked: List<Int>,
    toggleAlarmStatus: (Int) -> Unit,
    onPress: (Int) -> Unit,
    onLongPress: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(scheduleList.size) {
        if (scheduleList.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(scheduleList, key = { it.id }) { alarm ->
                CardScheduledAlarm(
                    timeText = alarm.timeFormatted,
                    taskName = alarm.taskName,
                    isActive = alarm.isActivated,
                    selectedDaysOfWeek = alarm.repeatedDays,
                    onChanged = { toggleAlarmStatus(alarm.id) },
                    onClick = { onPress(alarm.id) },
                    onLongClick = { onLongPress(alarm.id) },
                    isSelectionMode = isSelectedMode,
                    isChecked = isChecked.contains(alarm.id)
                )
            }
        }
    }
}


