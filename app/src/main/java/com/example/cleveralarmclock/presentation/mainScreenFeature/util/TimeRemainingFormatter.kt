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

        val days = hours / 24
        val remainingHours = hours % 24

        val daysString = res.getQuantityString(R.plurals.days_count, days.toInt(), days.toInt())
        val hoursString = res.getQuantityString(R.plurals.hours_count, remainingHours.toInt(), remainingHours.toInt())
        val minutesString = res.getQuantityString(R.plurals.minutes_count, minutes.toInt(), minutes.toInt())

        return when {
            hours == 0L && minutes == 0L -> context.getString(R.string.will_ring_in_less_than_a_minute)
            days > 0L -> {
                when {
                    remainingHours == 0L -> context.getString(R.string.will_ring_in_format, daysString, "").trim()
                    else -> context.getString(R.string.will_ring_in_format, daysString, hoursString).trim()
                }
            }
            hours == 0L -> context.getString(R.string.will_ring_in_format, "", minutesString).trim()
            minutes == 0L -> context.getString(R.string.will_ring_in_format, hoursString, "").trim()
            else -> context.getString(R.string.will_ring_in_format, hoursString, minutesString)
        }
    }
}