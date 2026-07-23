package com.example.cleveralarmclock.core.domain.alarm

interface AlarmSchedule{
    fun schedule(hour: Int, minute: Int, id: Int){}
    fun cancel(id: Int){}
}