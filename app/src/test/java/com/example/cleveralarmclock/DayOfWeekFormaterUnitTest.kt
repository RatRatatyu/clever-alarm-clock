package com.example.cleveralarmclock

import android.content.Context
import com.example.cleveralarmclock.core.domain.util.toFormattedString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.DayOfWeek

class DayOfWeekFormaterUnitTest {
    private val context: Context = mock()

    @Before
    fun setUp() {
        whenever(context.getString(R.string.custom)).thenReturn("Custom")
        whenever(context.getString(R.string.daily)).thenReturn("Daily")
        whenever(context.getString(R.string.once)).thenReturn("Once")
    }

    @Test
    fun dayOfWeekFormatterTester() {
        val firstTestSet = setOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY)
        val secondTestSet = emptySet<DayOfWeek>()
        val thirdTestSet = setOf(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
        )

        assertEquals(
            "Custom",
            firstTestSet.toFormattedString(isAllSelected = false, isForCard = true, context = context)
        )

        assertEquals(
            "Once",
            secondTestSet.toFormattedString(isAllSelected = false, isForCard = false, context = context)
        )

        assertEquals(
            "Daily",
            thirdTestSet.toFormattedString(isAllSelected = true, isForCard = false, context = context)
        )
    }
}