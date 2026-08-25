package com.example.cleveralarmclock.core.domain.usecase.manage

import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.schedule.ScheduleAlarmUseCase
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase
){
    suspend operator fun invoke(alarm: Alarm){

        val alarmId = alarmRepository.insertAlarm(alarm)

        scheduleAlarmUseCase(alarm.copy(id = alarmId.toInt()))
    }
}