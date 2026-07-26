package com.example.cleveralarmclock.core.domain.repository

import com.example.cleveralarmclock.core.domain.module.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAllAlarms(): Flow<List<AlarmModel>>
    suspend fun insertAlarm(alarm: AlarmModel): Long
    suspend fun deleteAlarmsByIds(ids: List<Int>)
    fun  getActiveAlarms(): Flow<List<AlarmModel>>
    suspend fun updateAlarm(alarm: AlarmModel)
    suspend fun getAlarmById(id:Int): AlarmModel?
}