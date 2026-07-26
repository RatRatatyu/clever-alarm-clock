package com.example.cleveralarmclock.core.data.repository

import com.example.cleveralarmclock.core.data.database.dao.AlarmDao
import com.example.cleveralarmclock.core.data.mapper.AlarmMapper
import com.example.cleveralarmclock.core.domain.module.AlarmModel
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmMapper: AlarmMapper
) : AlarmRepository {

    override fun getAllAlarms(): Flow<List<AlarmModel>> {
        return alarmDao.getAllAlarmClock().map { entityList->
            with(alarmMapper) {entityList.toDomainList()}
        }
    }

    override suspend fun insertAlarm(alarm: AlarmModel): Long {
        val entity = with(alarmMapper) { alarm.toEntity() }

        return alarmDao.insert(entity)
    }

    override suspend fun deleteAlarmsByIds(ids: List<Int>) {
        alarmDao.deleteAlarmsByIds(ids)
    }

    override fun getActiveAlarms(): Flow<List<AlarmModel>> {
        return alarmDao.getActiveAlarms().map { entityList ->
            with(alarmMapper) {entityList.toDomainList()}
        }
    }

    override suspend fun updateAlarm(alarm: AlarmModel) {
        val entity = with(alarmMapper){alarm.toEntity()}

        alarmDao.update(entity)
    }

    override suspend fun getAlarmById(id: Int): AlarmModel? {
        val entity = alarmDao.getAlarmById(id) ?: return null

        return with(alarmMapper) { entity.toDomain() }
    }
}