package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.mapper.AlarmMapper
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.module.AlarmModel
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule,
    private val alarmMapper: AlarmMapper
){
    suspend operator fun invoke(alarm: AlarmModel){

        val alarmId = alarmRepository.insertAlarm(alarm)

        alarmSchedule.schedule(
            hour = alarm.hours,
            minute = alarm.minutes,
            id = alarmId.toInt()
        )
    }
}