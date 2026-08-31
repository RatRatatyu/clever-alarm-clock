package com.example.cleveralarmclock.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "alarmEntity")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val taskId: String,
    val repeatDays: Set<DayOfWeek>,
    val isRepeated: Boolean = true,
    val isActivate: Boolean = true,
    val lastDismissed: LocalDate? = null
)