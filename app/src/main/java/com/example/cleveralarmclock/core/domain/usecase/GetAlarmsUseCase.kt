package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.data.repository.AlarmRepository
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    val alarmRepository: AlarmRepository,
) {

    operator fun invoke(): Flow<List<AlarmEntity>> {
        return alarmRepository.getAllAlarms()
    }
}