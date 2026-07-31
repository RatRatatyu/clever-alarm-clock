package com.example.cleveralarmclock.core.domain.module

import java.time.DayOfWeek

data class Alarm (
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val taskId: Int,
    val repeatDays: Set<DayOfWeek>,
    val isRepeated: Boolean = true,
    val isActivate: Boolean = true
)
