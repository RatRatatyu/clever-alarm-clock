package com.example.cleveralarmclock.core.service.alarmHandler.receiver_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cleveralarmclock.MainActivity
import com.example.cleveralarmclock.R

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return

        if (intent?.action == "android.intent.action.BOOT_COMPLETED"){
            // TODO
        }else{
            Log.i("ALARM_DEBUG", "Время пришло! Показываем уведомление звонка.")
            val startAppIntent = Intent(ctx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("ALARM_TRIGGERED",true)
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                0,
                startAppIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val channelId = "chanelId"

            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java) as NotificationManager
            val channel = NotificationChannel(
                channelId,
                ctx.getString(R.string.notification_title),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ctx.getString(R.string.notification_description)
            }

            notificationManager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(ctx.getString(R.string.notification_title))
                .setContentText(ctx.getString(R.string.notification_description))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setFullScreenIntent(pendingIntent, true)

            notificationManager.notify(1, builder.build())

        }
    }

}