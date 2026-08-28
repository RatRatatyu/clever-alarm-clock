package com.example.cleveralarmclock.core.service.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.domain.util.toPreNotificationId
import com.example.cleveralarmclock.core.notifications.NotificationFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreNotificationReceiver: BroadcastReceiver(){

    @Inject
    lateinit var notificationFactory: NotificationFactory

    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return

        val alarmId = intent?.getIntExtra("ALARM_ID", -1) ?: -1
        val isRepeated = intent?.getBooleanExtra("IS_REPEATED", false) ?: false
        val alarmTime = intent?.getStringExtra("ALARM_TIME")

        if (alarmId == -1) {
            Log.i("ANDROID_DEBUG", "error")
            return
        }

        val preNotificationId = alarmId.toPreNotificationId()

        val dismissIntent = Intent(context, AlarmActionReceiver::class.java).apply {
            action = ACTION_DISMISS_TODAY
            putExtra("ALARM_ID", alarmId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId.plus(200),
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val turnOffIntent = Intent(context, AlarmActionReceiver::class.java).apply {
            action = ACTION_TURN_OFF_PERMANENTLY
            putExtra("ALARM_ID", alarmId)
        }
        val turnOffPendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId.plus(300),
            turnOffIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = notificationFactory.createPreNotification(
            dismissPendingIntent,
            turnOffPendingIntent,
            isRepeated,
            alarmTime
        )

        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(preNotificationId, notification)
    }

    companion object {
        const val ACTION_DISMISS_TODAY = "com.example.cleveralarmclock.ACTION_DISMISS_TODAY"
        const val ACTION_TURN_OFF_PERMANENTLY = "com.example.cleveralarmclock.ACTION_TURN_OFF_PERMANENTLY"
    }

}