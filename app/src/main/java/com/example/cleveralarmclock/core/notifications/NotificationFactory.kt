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
import javax.inject.Singleton

@Singleton
class NotificationFactory @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }
    companion object {
        const val CHANNEL_RING_ID = "alarm_ring_channel"
        const val CHANNEL_PRE_ID = "alarm_pre_channel"
    }

    private fun createNotificationChannels() {
        val ringChannel = NotificationChannel(
            CHANNEL_RING_ID,
            context.getString(R.string.notification_title),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(null, null)
        }

        val preChannel = NotificationChannel(
            CHANNEL_PRE_ID,
            context.getString(R.string.forewarning),
            NotificationManager.IMPORTANCE_LOW
        )

        notificationManager.createNotificationChannels(listOf(ringChannel, preChannel))
    }

    fun createRingNotification(fullScreenPendingIntent: PendingIntent): Notification {
        return NotificationCompat.Builder(context, CHANNEL_RING_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(context.getString(R.string.alarm))
            .setContentText(context.getString(R.string.it_s_time_to_get_up))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setOngoing(true)
            .build()
    }

    fun createPreNotification(
        dismissPendingIntent: PendingIntent,
        turnOffPendingIntent: PendingIntent,
        isRepeated: Boolean,
        alarmTime: String?
    ): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_PRE_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(context.getString(R.string.upcoming_alarm))
            .setContentText(
                if (alarmTime != null){
                    context.getString(R.string.alarm_will_go_off_at, alarmTime)
                }else{
                    "Alarm will go off soon"
                }
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)

        if (isRepeated) {
            builder.addAction(
                R.drawable.ic_skip,
                context.getString(R.string.skip_for_today),
                dismissPendingIntent
            )
        }

        builder.addAction(
            R.drawable.ic_notifications_off,
            context.getString(R.string.turn_off_completely),
            turnOffPendingIntent
        )

        return builder.build()
    }
}