package com.example.cleveralarmclock.presentation.mainScreenFeature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale


@Composable
fun AlarmList(
    modifier: Modifier = Modifier,
    scheduleList: List<AlarmEntity>,
    nextAlarmText: String?,
    isSelectedMode: Boolean,
    isChecked: List<Int>,
    toggleAlarmStatus: (AlarmEntity) -> Unit,
    onPress: (AlarmEntity) -> Unit,
    onLongPress: (AlarmEntity) -> Unit,
){

    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        if(scheduleList.isEmpty()){
            Text(
                stringResource(R.string.you_have_no_alarm_clocks_yet_press_to_add),
                Modifier.padding(20.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }else{
            nextAlarmText?.let { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(scheduleList, key= {it.id} ){ alarm ->
                    CardScheduledAlarm(
                        hour = "%02d".format(LocalLocale.current.platformLocale, alarm.hours),
                        minute = "%02d".format(LocalLocale.current.platformLocale, alarm.minutes),
                        isActive = alarm.isActivate,
                        onChanged = {toggleAlarmStatus(alarm)},
                        onClick = {onPress(alarm)},
                        onLongClick = {onLongPress(alarm)},
                        isSelectionMode = isSelectedMode,
                        isChecked = isChecked.contains(alarm.id)
                    )
                }
            }
        }
    }
}


