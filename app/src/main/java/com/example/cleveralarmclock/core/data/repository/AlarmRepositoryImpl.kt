package com.example.cleveralarmclock.core.data.repository

import com.example.cleveralarmclock.core.data.database.dao.AlarmDao
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override fun getAllAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarmClock()
    }

    override suspend fun insertAlarm(alarm: AlarmEntity): Long {
        return alarmDao.insert(alarm)
    }

    override suspend fun deleteAlarmsByIds(ids: List<Int>) {
        alarmDao.deleteAlarmsByIds(ids)
    }

    override fun getActiveAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getActiveAlarms()
    }

    override suspend fun updateAlarm(alarm: AlarmEntity) {
        alarmDao.update(alarm)
    }

    override suspend fun getAlarmById(id: Int): AlarmEntity? {
        return alarmDao.getAlarmById(id)
    }
}