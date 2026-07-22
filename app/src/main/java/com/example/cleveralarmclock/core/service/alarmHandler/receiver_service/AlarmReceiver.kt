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
        val alarmId = intent?.getIntExtra("ALARM_ID", 0)
        Log.i("AlARM_DEBUG", "$alarmId")


        if (intent?.action == "android.intent.action.BOOT_COMPLETED"){
            // TODO
        }else{

            Log.i("ALARM_DEBUG", "Время пришло! Показываем уведомление звонка.")
            val foregroundServiceIntent = Intent(ctx, AlarmService::class.java).apply {
                putExtra("ALARM_ID", alarmId)
            }
            ctx.startForegroundService(foregroundServiceIntent)

        }
    }

}