package com.example.cleveralarmclock.core.domain.alarm

import java.time.DayOfWeek

interface AlarmSchedule{
    fun schedule(hour: Int, minute: Int, repeatDays: Set<DayOfWeek>, id: Int){}
    fun snoozeFor10seconds(id: Int){}
    fun cancel(id: Int){}
}