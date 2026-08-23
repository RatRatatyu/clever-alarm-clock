package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.usecase.manage.ToggleAlarmUseCase
import javax.inject.Inject

class RescheduleNextAlarmUseCase @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val alarmSchedule: AlarmSchedule,
    private val toggleAlarmUseCase: ToggleAlarmUseCase
){
    suspend operator fun invoke(alarmId: Int){
        val alarm = getAlarmByIdUseCase(alarmId) ?: return

        if(alarm.isRepeated){
            alarmSchedule.schedule(
                alarm.hours,
                alarm.minutes,
                alarm.repeatDays,
                alarm.id)

        }else{
            toggleAlarmUseCase(alarm.id)
        }
    }
}