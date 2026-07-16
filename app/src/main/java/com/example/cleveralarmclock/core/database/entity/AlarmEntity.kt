package com.example.cleveralarmclock.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmEntity")
data class AlarmEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hours: Int,
    val minutes: Int,
    val isActivate: Boolean = true
)