package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule,
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