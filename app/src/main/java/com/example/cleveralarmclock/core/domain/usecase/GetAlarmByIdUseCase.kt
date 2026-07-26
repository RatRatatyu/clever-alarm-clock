package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.module.AlarmModel
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarmId: Int): AlarmModel?{
        return repository.getAlarmById(alarmId)

    }
}