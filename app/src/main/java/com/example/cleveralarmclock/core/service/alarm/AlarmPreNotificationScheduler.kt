package com.example.cleveralarmclock.core.service.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.cleveralarmclock.core.domain.util.toPreNotificationId
import com.example.cleveralarmclock.core.notifications.NotificationFactory
import com.example.cleveralarmclock.core.service.receivers.PreNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmPreNotificationScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context,
){

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun preNotificationScheduler(alarmId: Int, alarmTime: LocalDateTime){
        val preNotificationId = alarmId.toPreNotificationId()
        val preTime = alarmTime.minusMinutes(10)

        if (preTime.isBefore(LocalDateTime.now())) return

        val intent = Intent(context, PreNotificationReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId,
            intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime= preTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )



    }

    fun cancelPreNotification(alarmId: Int){
        val preNotificationId = alarmId.toPreNotificationId()
        val intent = Intent(context, PreNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)
    }
}

