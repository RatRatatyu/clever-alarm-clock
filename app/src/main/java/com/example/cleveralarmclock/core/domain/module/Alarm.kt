package com.example.cleveralarmclock.core.domain.module

import com.example.cleveralarmclock.core.domain.task.TaskType
import java.time.DayOfWeek
import java.time.LocalDate

data class Alarm (
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val taskId: TaskType,
    val repeatDays: Set<DayOfWeek>,
    val isRepeated: Boolean = true,
    val isActivate: Boolean = true,
    val lastDismissed: LocalDate? = null
)
