package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import java.time.LocalDate
import javax.inject.Inject

class ScheduleAlarmUseCase @Inject constructor(
    private val alarmSchedule: AlarmSchedule,
) {
    suspend operator fun invoke(alarm: Alarm, skipToday: Boolean = false) {
        var isSkipped = skipToday

        if(!alarm.isActivate){
            alarmSchedule.cancel(alarm.id)
            return
        }

        if (alarm.lastDismissed == LocalDate.now()) isSkipped = true

        val triggerTime = AlarmTimeCalculator.calculateNextTriggerTime(
            alarm.hours,
            alarm.minutes,
            alarm.repeatDays,
            isSkipped
        )

        alarmSchedule.schedule(alarm.id, triggerTime)
    }
}