package com.example.cleveralarmclock.core.domain.repository

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAllAlarms(): Flow<List<AlarmEntity>>
    suspend fun insertAlarm(alarm: AlarmEntity): Long
    suspend fun deleteAlarmsByIds(ids: List<Int>)
    fun  getActiveAlarms(): Flow<List<AlarmEntity>>
    suspend fun updateAlarm(alarm: AlarmEntity)
    suspend fun getAlarmById(id:Int): AlarmEntity?
}