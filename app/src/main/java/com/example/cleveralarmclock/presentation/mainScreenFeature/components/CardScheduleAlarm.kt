package com.example.cleveralarmclock.presentation.mainScreenFeature.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.core.domain.util.toFormattedString
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle as dayOfWeekFormater

@Composable
fun CardScheduledAlarm(
    modifier: Modifier = Modifier,
    timeText: String,
    taskName: Int?,
    isActive: Boolean,
    lastDismissed: LocalDate?,
    selectedDaysOfWeek: Set<DayOfWeek>,
    onChanged: () -> Unit,
    onClick: ()-> Unit,
    onLongClick: () -> Unit,
    isSelectionMode: Boolean,
    isChecked: Boolean
) {

    val baseColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val containerColor = if (lastDismissed == LocalDate.now()) baseColor.copy(alpha = 0.6f) else baseColor

    val contentColor = if (isActive) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onSurfaceVariant

    val inactiveDayColor = contentColor.copy(alpha = 0.4f)

    val baseThumbColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    val thumbColor = if (lastDismissed == LocalDate.now()) baseThumbColor.copy(alpha = 0.6f) else baseThumbColor

    val trackColor = if (isActive) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.surfaceVariant

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor
        ),
    ) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                // Alarm time and task name
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = timeText,
                        style = MaterialTheme.typography.headlineLarge,
                        color = contentColor
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    taskName?.let { resId ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        ) {
                            Text(
                                text = stringResource(id = resId),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Alarm Status
                Row(Modifier) {
                    Text(
                        text = selectedDaysOfWeek.toFormattedString(
                            isAllSelected = selectedDaysOfWeek.size == 7,
                            isForCard = true,
                            context = LocalContext.current
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = contentColor.copy(alpha = 0.8f)
                    )

                    lastDismissed?.let { data ->
                        if (data == LocalDate.now()){
                            val formatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())

                            val shortMonth = data
                                .format(formatter)
                                .lowercase()

                            Text(
                                text = ": Dismiss for ${data.dayOfMonth} $shortMonth",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = contentColor.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Days of weeks
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DayOfWeek.entries.forEach { dayOfWeek ->
                        val isSelected = selectedDaysOfWeek.contains(dayOfWeek)
                        Text(
                            text = dayOfWeek.getDisplayName(dayOfWeekFormater.SHORT, Locale.getDefault()),
                            color = if (isSelected) contentColor else inactiveDayColor,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                if(isSelectionMode){
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { onClick() },

                        colors = CheckboxDefaults.colors(
                            checkedColor = if (isActive) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.primary,

                            checkmarkColor = if (isActive) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onPrimary,

                            uncheckedColor = if (isActive) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                else{
                    Switch(
                        checked = isActive,
                        onCheckedChange = { onChanged() },

                        colors = SwitchDefaults.colors(
                            checkedThumbColor = thumbColor,
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,

                            checkedTrackColor = trackColor,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surface,

                            checkedBorderColor = Color.Transparent,
                            uncheckedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardSchedulePreview(){
    MaterialTheme{
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CardScheduledAlarm(
                timeText = "05:20 PM",
                taskName = null,
                isActive = true,
                lastDismissed = null,
                selectedDaysOfWeek = setOf(),
                onChanged = {},
                onClick = {},
                onLongClick = {},
                isSelectionMode = false,
                isChecked = false
            )

            CardScheduledAlarm(
                timeText = "05:20 PM",
                taskName = null,
                isActive = true,
                lastDismissed = LocalDate.now(),
                selectedDaysOfWeek = setOf(),
                onChanged = {},
                onClick = {},
                onLongClick = {},
                isSelectionMode = true,
                isChecked = false
            )
        }
    }
}

