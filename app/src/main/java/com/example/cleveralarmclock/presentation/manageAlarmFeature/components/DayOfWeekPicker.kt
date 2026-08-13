package com.example.cleveralarmclock.presentation.manageAlarmFeature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.domain.util.toFormattedString
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun DayOfWeekPicker (
    modifier: Modifier = Modifier,
    isAlDaysSelected: Boolean,
    selectedDayOfWeek: Set<DayOfWeek>,
    selectAllDayOfWeek: () -> Unit,
    onSelectDayOfWeek: (DayOfWeek) -> Unit
){
    Column(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = selectedDayOfWeek.toFormattedString(
                    isAllSelected = isAlDaysSelected,
                    context = LocalContext.current),
                style = MaterialTheme.typography.titleMedium
            )


            IconButton({selectAllDayOfWeek()}) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_all_weekdays)
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DayOfWeek.entries.forEach { day ->
                val isSelected = selectedDayOfWeek.contains(day)

                val backgroundColor = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                val textColor = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .aspectRatio(1f)
                        .background(backgroundColor)
                        .clickable { onSelectDayOfWeek(day) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}