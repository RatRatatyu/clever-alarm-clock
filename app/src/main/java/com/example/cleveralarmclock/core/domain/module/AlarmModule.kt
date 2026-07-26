package com.example.cleveralarmclock.core.domain.module

data class AlarmModel (
    val id: Int = 0,
    val hours: Int,
    val minutes: Int,
    val colorHex: String = "#FF0000", // for testing, later will be changed to tasks id
    val isActivate: Boolean = true
)
