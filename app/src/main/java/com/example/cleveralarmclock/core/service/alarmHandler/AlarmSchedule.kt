package com.example.cleveralarmclock.core.service.alarmHandler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedule @Inject constructor(
    @ApplicationContext private val context: Context
){
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    @SuppressLint("MissingPermission")
    fun schedule(){
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        var localTime = LocalDateTime.now().plusSeconds(5) //for testing
//            .withHour(0)
//            .withMinute(0)
//            .withSecond(5)
//            .withNano(0)

        if (localTime.isBefore(LocalDateTime.now())) {
            localTime = localTime.plusDays(1)
        }

        val triggerTime = localTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val info = AlarmManager.AlarmClockInfo(
            triggerTime,
            pendingIntent
        )

        Log.i("ALARM_DEBUG","${LocalDateTime.now()}")

        Log.i("ALARM_DEBUG","${localTime}")

        alarmManager?.setAlarmClock(
            info,
            pendingIntent
        )
        Log.i("ALARM_DEBUG","Будильник устоновлен")
    }

    fun cancel(){
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.i("ALARM_DEBUG","Будильник отменен")
        alarmManager?.cancel(pendingIntent)
    }
}


