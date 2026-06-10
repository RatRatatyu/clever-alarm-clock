package com.example.cleveralarmclock.alarmHandler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cleveralarmclock.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val startAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("ALARM_TRIGGERED",true)
        }
        context?.startActivity(startAppIntent)
    }

}