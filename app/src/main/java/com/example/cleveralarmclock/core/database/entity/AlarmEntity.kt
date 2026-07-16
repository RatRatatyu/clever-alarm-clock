package com.example.cleveralarmclock.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmEntity")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val colorHex: String = "#FF0000", // for testing, later will be changed to tasks id
    val isActivate: Boolean = true
)