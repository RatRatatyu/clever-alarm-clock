package com.example.cleveralarmclock.core.service.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import com.example.cleveralarmclock.core.domain.util.toSnoozeId
import com.example.cleveralarmclock.core.service.receivers.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmScheduleImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val alarmPreNotificationScheduler: AlarmPreNotificationScheduler
): AlarmSchedule{
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    @SuppressLint("MissingPermission")
    override fun schedule(hour: Int, minute: Int, repeatDays: Set<DayOfWeek>, id: Int){

        val localTime = AlarmTimeCalculator.calculateNextTriggerTime(
            hour,
            minute,
            repeatDays
        )

        helper(id, localTime)
        alarmPreNotificationScheduler.preNotificationScheduler(id, localTime)

    }


    override fun snoozeFor10seconds(id: Int) {
        val snoozeTime = LocalDateTime.now().plusMinutes(10)
        helper(id.toSnoozeId(), snoozeTime)
        alarmPreNotificationScheduler.preNotificationScheduler(id, snoozeTime)

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
        alarmPreNotificationScheduler.cancelPreNotification(id)
    }

    @SuppressLint("MissingPermission")
    private fun helper(
        alarmId: Int,
        timeToSet: LocalDateTime
    ){
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = timeToSet
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val info = AlarmManager.AlarmClockInfo(
            triggerTime,
            pendingIntent
        )
        alarmManager?.setAlarmClock(
            info,
            pendingIntent
        )
    }
}

