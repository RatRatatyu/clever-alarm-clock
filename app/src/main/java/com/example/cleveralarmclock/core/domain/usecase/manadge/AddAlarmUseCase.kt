package com.example.cleveralarmclock.core.domain.usecase.manadge

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule,
){
    suspend operator fun invoke(alarm: Alarm){

        val alarmId = alarmRepository.insertAlarm(alarm)

        alarmSchedule.schedule(
            hour = alarm.hours,
            minute = alarm.minutes,
            repeatDays = alarm.repeatDays,
            id = alarmId.toInt()
        )
    }
}