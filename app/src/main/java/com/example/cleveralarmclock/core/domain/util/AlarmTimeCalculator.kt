package com.example.cleveralarmclock.core.domain.util

import java.time.DayOfWeek
import java.time.LocalDateTime

object AlarmTimeCalculator {

    fun calculateNextTriggerTime(
        hour: Int,
        minute: Int,
        repeatDays: Set<DayOfWeek>,
        skipForToday: Boolean = false,
        now: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime {

        val effectiveNow = if (skipForToday) {
            now.withHour(23).withMinute(59).withSecond(59)
        } else {
            now
        }

        var target = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        // For one-time alarm
        if (repeatDays.isEmpty()) {
            if (skipForToday || target.isBefore(effectiveNow) || target.isEqual(effectiveNow)) {
                target = target.plusDays(1)
            }
            return target
        }

        // For a repeating alarm
        while (!repeatDays.contains(target.dayOfWeek) || target.isBefore(effectiveNow) || target.isEqual(effectiveNow)) {
            target = target.plusDays(1)
        }

        return target
    }
}
