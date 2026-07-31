package com.example.cleveralarmclock.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmEntity")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val taskId: Int,
    val repeatDays: String,
    val isRepeated: Boolean = true,
    val isActivate: Boolean = true
)