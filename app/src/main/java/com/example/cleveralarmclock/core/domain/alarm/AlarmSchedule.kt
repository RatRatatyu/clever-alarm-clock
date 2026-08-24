package com.example.cleveralarmclock.core.domain.alarm

import java.time.DayOfWeek

interface AlarmSchedule{
    suspend fun schedule(
        hour: Int,
        minute: Int,
        repeatDays: Set<DayOfWeek>,
        id: Int,
        skipForToday: Boolean = false
    )
    suspend fun snoozeFor10seconds(id: Int)
    fun cancel(id: Int)
}