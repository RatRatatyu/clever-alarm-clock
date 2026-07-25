package com.example.cleveralarmclock.presentation.manageAlarmFeature.util

import android.content.Context
import android.text.format.DateFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalTime
import javax.inject.Inject

data class ReturnFormat(
    val hour: Int,
    val amPm: String,
    val is24Format: Boolean
)


class DataTimeFormatter @Inject constructor(
    @ApplicationContext private val context: Context
){


    fun convert24To12Hour(hour24: Int): ReturnFormat{
        val is24Hour = DateFormat.is24HourFormat(context)

        var hour = if(hour24 != -1) hour24 else LocalTime.now().hour
        var amPm = "AM"

        if (!is24Hour) {
            amPm = if (hour < 12) "AM" else "PM"
            val hour12 = hour % 12
            hour = if (hour12 == 0) 12 else hour12
        }
        return ReturnFormat(hour, amPm, is24Hour)
    }

    fun convert12To24Hour(hour: Int, amPm: String): Int {
        val is24Hour = DateFormat.is24HourFormat(context)

        if (is24Hour) return hour

        return if (amPm == "AM") {
            if (hour == 12) 0 else hour
        } else {
            if (hour == 12) 12 else hour + 12
        }
    }
}