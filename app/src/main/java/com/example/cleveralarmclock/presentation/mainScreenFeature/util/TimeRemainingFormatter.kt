package com.example.cleveralarmclock.presentation.mainScreenFeature.util


import com.example.cleveralarmclock.R
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class TimeRemainingFormatter @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun format(hours: Long, minutes: Long): String {
        val res = context.resources
        val hoursString = res.getQuantityString(R.plurals.hours_count, hours.toInt(), hours.toInt())
        val minutesString = res.getQuantityString(R.plurals.minutes_count, minutes.toInt(), minutes.toInt())

        return when {
            hours == 0L && minutes == 0L -> context.getString(R.string.will_ring_in_less_than_a_minute)
            hours == 0L -> context.getString(R.string.will_ring_in_format, "", minutesString).trim()
            minutes == 0L -> context.getString(R.string.will_ring_in_format, hoursString, "").trim()
            else -> context.getString(R.string.will_ring_in_format, hoursString, minutesString)
        }
    }
}