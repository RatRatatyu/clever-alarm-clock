package com.example.cleveralarmclock.core.domain.repository

import com.example.cleveralarmclock.core.domain.module.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAllAlarms(): Flow<List<Alarm>>
    suspend fun insertAlarm(alarm: Alarm): Long
    suspend fun deleteAlarmsByIds(ids: List<Int>)
    fun  getActiveAlarms(): Flow<List<Alarm>>
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun getAlarmById(id:Int): Alarm?
}