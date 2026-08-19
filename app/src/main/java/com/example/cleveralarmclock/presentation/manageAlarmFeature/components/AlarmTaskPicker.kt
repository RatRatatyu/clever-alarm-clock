package com.example.cleveralarmclock.presentation.manageAlarmFeature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleveralarmclock.core.domain.task.AlarmTask
import com.example.cleveralarmclock.core.domain.task.TaskType


@Composable
fun AlarmTaskPicker(
    modifier: Modifier = Modifier,
    alarmTaskList: List<AlarmTask>,
    selectedTask: TaskType,
    onTaskSelected: (TaskType) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Simple manual grid implementation for small number of items
        val chunkedTasks = alarmTaskList.chunked(2)
        chunkedTasks.forEach { rowTasks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowTasks.forEach { task ->
                    AlarmTaskItem(
                        task = task,
                        isSelected = selectedTask == task.type,
                        onClick = { onTaskSelected(task.type) },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowTasks.size < 2) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AlarmTaskItem(
    task: AlarmTask,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = modifier.defaultMinSize(minHeight = 90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = task.iconResId),
                    contentDescription = stringResource(id = task.titleResId),
                )

                Text(
                    text = stringResource(id = task.titleResId),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Text(
                text = stringResource(id = task.descriptionResId),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}