package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.data.repository.AlarmRepository
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.service.alarmHandler.domain.AlarmSchedule
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
    val alarmRepository: AlarmRepository,
    val alarmSchedule: AlarmSchedule
) {

    suspend operator fun invoke(alarm: AlarmEntity){

        val updatedAlarm = alarm.copy(isActivate = !alarm.isActivate)
        alarmRepository.updateAlarm(updatedAlarm)

        if (updatedAlarm.isActivate) {
            alarmSchedule.schedule(
                updatedAlarm.hours,
                updatedAlarm.minutes,
                id = updatedAlarm.id)
        } else {
            alarmSchedule.cancel(alarm.id) // later will passed updatedAlarm.id
        }
    }
}