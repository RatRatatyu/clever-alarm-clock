package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val alarmSchedule: AlarmSchedule
) {

    suspend operator fun invoke(alarmId: Int){

        val alarm = getAlarmByIdUseCase(alarmId) ?: return

        val updatedAlarm = alarm.copy(isActivate = !alarm.isActivate)
        alarmRepository.updateAlarm(updatedAlarm)

        if (updatedAlarm.isActivate) {
            alarmSchedule.schedule(
                updatedAlarm.hours,
                updatedAlarm.minutes,
                updatedAlarm.repeatDays,
                id = updatedAlarm.id)
        } else {
            alarmSchedule.cancel(alarmId)
        }
    }
}