package com.example.cleveralarmclock.core.domain.alarm

import java.time.LocalDateTime

interface AlarmSchedule{
    suspend fun schedule(id: Int, triggerTime: LocalDateTime)
    suspend fun snoozeFor10seconds(id: Int)
    fun cancel(id: Int)
}