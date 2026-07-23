package com.example.cleveralarmclock.core.service.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.service.services.AlarmService

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