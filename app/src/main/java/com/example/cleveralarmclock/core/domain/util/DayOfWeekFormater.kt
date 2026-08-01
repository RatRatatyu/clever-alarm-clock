package com.example.cleveralarmclock.core.domain.util

import android.content.Context
import com.example.cleveralarmclock.R
import java.time.DayOfWeek
import java.util.Locale
import java.time.format.TextStyle


fun Set<DayOfWeek>.toFormattedString(
    isAllSelected: Boolean,
    isForCard: Boolean = false,
    context: Context
): String {
    if (isForCard && !isAllSelected && isNotEmpty()) return context.getString(R.string.custom)

    return when {
        isAllSelected -> context.getString(R.string.daily)
        isEmpty() -> context.getString(R.string.once)
        else -> this
            .sortedBy { it.value }
            .joinToString(", ") { day ->
                day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            }
    }
}