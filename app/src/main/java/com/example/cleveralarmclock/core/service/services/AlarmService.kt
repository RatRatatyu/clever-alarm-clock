package com.example.cleveralarmclock.core.service.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.example.cleveralarmclock.core.domain.alarm.AlarmPlayer
import com.example.cleveralarmclock.core.notifications.NotificationFactory
import com.example.cleveralarmclock.presentation.alarmAlertFeature.AlarmAlertActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService: Service() {

    @Inject
    lateinit var alarmPlayer: AlarmPlayer

    @Inject
    lateinit var notificationFactory: NotificationFactory

    override fun onCreate() {
        super.onCreate()
        alarmPlayer.registrationPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getIntExtra("ALARM_ID", 0)

        val startAppIntent = Intent(this, AlarmAlertActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("ALARM_ID", alarmId)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            alarmId ?: 0,
            startAppIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmPlayer.startPlayer()
        val notificationBuilder = notificationFactory.showNotification(pendingIntent)

        ServiceCompat.startForeground(
            this,
             100,
            notificationBuilder,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            } else {
                0
            },
        )
        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        alarmPlayer.stopPlayer()
    }

    override fun onBind(intent: Intent?): IBinder? = null

}