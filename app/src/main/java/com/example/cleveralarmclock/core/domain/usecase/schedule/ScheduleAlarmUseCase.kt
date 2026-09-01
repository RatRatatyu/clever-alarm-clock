package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import java.time.LocalDate
import javax.inject.Inject

class ScheduleAlarmUseCase @Inject constructor(
    private val alarmSchedule: AlarmSchedule,
) {
    suspend operator fun invoke(alarm: Alarm) {
        if(!alarm.isActivate){
            alarmSchedule.cancel(alarm.id)
            return
        }

        val triggerTime = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = alarm.hours,
            minute = alarm.minutes,
            repeatDays = alarm.repeatDays,
            skipForToday = alarm.lastDismissed == LocalDate.now()
        )

        alarmSchedule.schedule(alarm.id, triggerTime)
    }
}