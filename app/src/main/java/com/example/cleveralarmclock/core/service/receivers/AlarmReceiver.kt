package com.example.cleveralarmclock.core.service.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cleveralarmclock.core.service.services.AlarmService

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return
        val alarmId = intent?.getIntExtra("ALARM_ID", 0)

        val foregroundServiceIntent = Intent(ctx, AlarmService::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }
        ctx.startForegroundService(foregroundServiceIntent)
    }

}