package com.example.cleveralarmclock.core.data.database.converters

import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AlarmConverters {

    // Converts for Set<DayOfWeek>
    @TypeConverter
    fun fromRepeatDays(days: Set<DayOfWeek>?): String{
        return days?.joinToString(",") {it.value.toString()} ?: ""
    }

    @TypeConverter
    fun toRepeatDays(data: String?): Set<DayOfWeek>{
        if(data.isNullOrEmpty()) return emptySet()
        return data.split(",")
            .mapNotNull { it.toIntOrNull() }
            .map { DayOfWeek.of(it) }
            .toSet()
    }

    //Converts forLocaleDate
    @TypeConverter
    fun fromLocaleDate(date: LocalDate?): String?{
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate?{
        return dateString?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }
}