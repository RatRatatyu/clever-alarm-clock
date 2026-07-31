package com.example.cleveralarmclock.core.domain.util

import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject


object AlarmTimeCalculator {

    fun calculateNextTriggerTime(
        hour: Int,
        minute: Int,
        repeatDays: Set<DayOfWeek>,
        now: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime {

        var target = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if(repeatDays.isEmpty()){
            if (target.isBefore(now) || target.isEqual(now)) {
                target = target.plusDays(1)
            }
            return target
        }

        while (!repeatDays.contains(target.dayOfWeek) || target.isBefore(now) || target.isEqual(now)) {
            target = target.plusDays(1)
        }

        return target
    }
}
