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
import java.util.Locale

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    is24Hours: Boolean,
    selectedHours: Int,
    selectedMinutes: Int,
    selectedAmPm: String = "",
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onAmPmChange: (String) -> Unit = {}
){


    val hoursList = remember(is24Hours) {
        val size = if (is24Hours) 24 else 12
        val offset = if (is24Hours) 0 else 1
        List(size) { index -> index + offset}
    }
    val minutesList = remember { List(60) { index -> index }}
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
            if (!is24Hours) {
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
                        initialItem = selectedHours,
                        onItemSelected = { hours -> onHoursChange(hours) },
                        displayText = { String.format(Locale.getDefault(), "%02d", it) }
                    )
                }

                // MINUTE COLUMN
                Column (
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    WheelPicker(
                        items = minutesList,
                        initialItem = selectedMinutes,
                        onItemSelected = { minutes -> onMinutesChange(minutes) },
                        displayText = { String.format(Locale.getDefault(), "%02d", it) }
                    )
                }

                if(!is24Hours){
                    // AM PM COLUMN
                    Column (
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        WheelPicker(
                            items = amPmList,
                            initialItem = selectedAmPm,
                            onItemSelected = { amPm -> onAmPmChange(amPm) },
                            isInfinite = false,
                            displayText = { it }
                        )
                    }
                }
            }
        }
    }
}
