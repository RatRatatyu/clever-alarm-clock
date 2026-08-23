package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import javax.inject.Inject

class DisableAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule
) {
    suspend operator fun invoke(id: Int) {
        val alarm = alarmRepository.getAlarmById(id) ?: return

        val updatedAlarm = alarm.copy(isActivate = !alarm.isActivate)
        alarmRepository.updateAlarm(updatedAlarm)

        alarmSchedule.cancel(id)
    }
}