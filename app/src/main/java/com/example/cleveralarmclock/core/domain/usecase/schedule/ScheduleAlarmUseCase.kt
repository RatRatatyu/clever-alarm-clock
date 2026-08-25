package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import javax.inject.Inject

class ScheduleAlarmUseCase @Inject constructor(
    private val alarmSchedule: AlarmSchedule,
) {
    suspend operator fun invoke(alarm: Alarm, skipToday: Boolean = false) {
        if(!alarm.isActivate){
            alarmSchedule.cancel(alarm.id)
            return
        }

        val triggerTime = AlarmTimeCalculator.calculateNextTriggerTime(
            alarm.hours,
            alarm.minutes,
            alarm.repeatDays,
            skipToday
        )

        alarmSchedule.schedule(alarm.id, triggerTime)
    }
}