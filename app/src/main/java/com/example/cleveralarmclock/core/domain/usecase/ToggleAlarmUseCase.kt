package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule
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