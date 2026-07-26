package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.module.AlarmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {

    operator fun invoke(): Flow<List<AlarmModel>> {
        return alarmRepository.getAllAlarms()
    }
}