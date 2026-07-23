package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {

    operator fun invoke(): Flow<List<AlarmEntity>> {
        return alarmRepository.getAllAlarms()
    }
}