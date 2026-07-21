package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.data.repository.AlarmRepository
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.service.alarmHandler.domain.AlarmSchedule
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    val alarmRepository: AlarmRepository,
    val alarmSchedule: AlarmSchedule,
){
    suspend operator fun invoke(alarm: AlarmEntity){
        val alarmId = alarmRepository.insertAlarm(alarm)

        alarmSchedule.schedule(
            hour = alarm.hours,
            minute = alarm.minutes,
            id = alarmId.toInt()
        )
    }
}