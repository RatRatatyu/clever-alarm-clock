package com.example.cleveralarmclock.core.domain.usecase.manage

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.schedule.ScheduleAlarmUseCase
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase
) {

    suspend operator fun invoke(alarmId: Int){

        val alarm = getAlarmByIdUseCase(alarmId) ?: return

        val updatedAlarm = alarm.copy(isActivate = !alarm.isActivate)
        alarmRepository.updateAlarm(updatedAlarm)

        scheduleAlarmUseCase(updatedAlarm)
    }
}