package com.example.cleveralarmclock.core.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.cleveralarmclock.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationFactory @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val channelId = "chanelId"

    fun showNotification(
        pendingIntent: PendingIntent
    ): Notification {
        val notificationManager: NotificationManager = context.getSystemService(
            NotificationManager::class.java) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.notification_title),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.notification_description)
        }

        notificationManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setFullScreenIntent(pendingIntent, true)
            .build()
    }
}