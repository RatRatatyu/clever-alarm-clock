package com.example.cleveralarmclock.core.domain.usecase.manage

import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {

    operator fun invoke(): Flow<List<Alarm>> {
        return alarmRepository.getAllAlarms()
    }
}