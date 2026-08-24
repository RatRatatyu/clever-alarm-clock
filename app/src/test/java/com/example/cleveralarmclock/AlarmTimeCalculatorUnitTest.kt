package com.example.cleveralarmclock

import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime

class AlarmTimeCalculatorUnitTest {

    private val fakeNow = LocalDateTime.of(2026, 8, 24, 10, 0)
    private val specialNow = LocalDateTime.of(2026, 12, 31, 10, 0)


    @Test
    fun firstTest() {
        val result = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = 9,
            minute = 0,
            repeatDays = setOf(),
            skipForToday = false,
            now = fakeNow
        )

        assertEquals(
            LocalDateTime.of(2026, 8, 25, 9, 0),
            result
        )
    }

    @Test
    fun secondTest() {
        val result = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = 10,
            minute = 0,
            repeatDays = setOf(),
            skipForToday = false,
            now = fakeNow
        )

        assertEquals(
            LocalDateTime.of(2026, 8, 25, 10, 0),
            result
        )
    }

    @Test
    fun thirdTest() {
        val result = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = 9,
            minute = 54,
            repeatDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
            skipForToday = true,
            now = fakeNow
        )

        assertEquals(
            LocalDateTime.of(2026, 8, 25, 9, 54),
            result
        )
    }

    @Test
    fun fourthTest() {
        val result = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = 9,
            minute = 54,
            repeatDays = setOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
            skipForToday = true,
            now = fakeNow
        )

        assertEquals(
            LocalDateTime.of(2026, 8, 28, 9, 54),
            result
        )
    }

    @Test
    fun fifthTest() {
        val result = AlarmTimeCalculator.calculateNextTriggerTime(
            hour = 9,
            minute = 0,
            repeatDays = setOf(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            skipForToday = true,
            now = specialNow
        )

        assertEquals(
            LocalDateTime.of(2027, 1, 1, 9, 0),
            result
        )
    }
}