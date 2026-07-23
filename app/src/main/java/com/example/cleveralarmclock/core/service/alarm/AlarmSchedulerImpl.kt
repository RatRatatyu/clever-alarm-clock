package com.example.cleveralarmclock.core.service.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.service.receivers.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmScheduleImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): AlarmSchedule{
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    @SuppressLint("MissingPermission")
    override fun schedule(hour: Int, minute: Int, id: Int){

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        var localTime = LocalDateTime.now()
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

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

        Log.i("ALARM_DEBUG","$localTime")

        alarmManager?.setAlarmClock(
            info,
            pendingIntent
        )
        Log.i("ALARM_DEBUG","Будильник устоновлен")
    }

    override fun cancel(id: Int){
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.i("ALARM_DEBUG","Будильник отменен $id")
        alarmManager?.cancel(pendingIntent)
    }
}