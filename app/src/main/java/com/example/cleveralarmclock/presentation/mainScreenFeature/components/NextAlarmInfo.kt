package com.example.cleveralarmclock.presentation.mainScreenFeature.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.presentation.mainScreenFeature.AlarmUiModel


@Composable
fun NextAlarmInfo(
    modifier: Modifier = Modifier,
    scheduleList: List<AlarmUiModel>,
    nextAlarmText: String?
) {
    if(scheduleList.isEmpty()){
        Text(
            stringResource(R.string.you_have_no_alarm_clocks_yet_press_to_add),
            modifier.padding(20.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }else {
        nextAlarmText?.let { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}