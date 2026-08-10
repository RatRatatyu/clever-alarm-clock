package com.example.cleveralarmclock

import android.content.Context
import com.example.cleveralarmclock.core.domain.util.DataTimeFormatter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class DataTimeFormatterUnitTest {

    private val context: Context = mock()
    private lateinit var formatter: DataTimeFormatter

    @Before
    fun setUp(){
        formatter = DataTimeFormatter(context)
    }

    @Test
    fun convert24To12HourTest(){
        val firstTestHour = formatter.convert24To12Hour(0)
        val secondTestHour = formatter.convert24To12Hour(15)
        val thirdTest = formatter.convert24To12Hour(-1)

        assertEquals(
            0, firstTestHour.hour
        )
        assertEquals(
            15, secondTestHour.hour
        )
        assertEquals(
            14,
            thirdTest.hour
        )
    }

    @Test
    fun convert12To24HourTest(){
        val firstTestHour = formatter.convert12To24Hour(2, "PM")
        val secondTestHour = formatter.convert12To24Hour(12, "AM")
        val thirdTest = formatter.convert12To24Hour(3, "PM")

        assertEquals(
            14, firstTestHour
        )
        assertEquals(
            12, secondTestHour
        )
        assertEquals(
            15, thirdTest
        )

    }
}