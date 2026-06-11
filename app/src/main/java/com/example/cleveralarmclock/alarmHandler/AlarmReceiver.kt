package com.example.cleveralarmclock.alarmHandler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cleveralarmclock.MainActivity
import com.example.cleveralarmclock.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val startAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("ALARM_TRIGGERED",true)
        }
        Log.i("ALARM_DEBUG","start")
        val ctx = context ?: return
        val fullScreenPendingIntent = PendingIntent.getActivity(
            ctx,
            0,
            startAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = "alarm_channel_id"

        val notificationManager = (ctx.getSystemService(Context.NOTIFICATION_SERVICE) ) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                ctx.getString(R.string.notification_title),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ctx.getString(R.string.notification_description)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(ctx.getString(R.string.notification_title))
            .setContentText(ctx.getString(R.string.notification_description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
        notificationManager.notify(1, notificationBuilder.build())
    }

}