package com.example.cleveralarmclock.core.domain.util

fun Int.toPreNotificationId():Int{
    return this + 10000
}

fun Int.toSnoozeId():Int{
    return this + 5000
}