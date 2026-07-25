package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarmId: Int): AlarmEntity?{
        return repository.getAlarmById(alarmId)

    }
}