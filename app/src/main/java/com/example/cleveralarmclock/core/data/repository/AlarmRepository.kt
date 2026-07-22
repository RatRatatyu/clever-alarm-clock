package com.example.cleveralarmclock.core.data.repository

import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow


interface AlarmRepository {
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    suspend fun insertAlarm(alarm: AlarmEntity): Long

    suspend fun deleteAlarm(alarm: AlarmEntity)

    fun  getActiveAlarms(): Flow<List<AlarmEntity>>
    suspend fun updateAlarm(alarm: AlarmEntity)

    suspend fun deleteById(alarmId: Int)
}