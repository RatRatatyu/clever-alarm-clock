package com.example.cleveralarmclock.alarmHandler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

class AlarmSchedule (private val context: Context){
    val intent = Intent(context, AlarmReceiver::class.java)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    fun schedule(){
        val triggerTime= Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND, 20)

            if(before(Calendar.getInstance())){
                add(Calendar.DATE, 1)
            }

        }


        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime.timeInMillis,
            pendingIntent
        )
        Log.i("ALARM_DEBUG","Будильник устоновлен")
    }

    fun cancel(){
        alarmManager?.cancel(pendingIntent)
    }
}


