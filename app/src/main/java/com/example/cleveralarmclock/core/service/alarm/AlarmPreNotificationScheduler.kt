package com.example.cleveralarmclock.core.service.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.util.DataTimeFormatter
import com.example.cleveralarmclock.core.domain.util.toPreNotificationId
import com.example.cleveralarmclock.core.service.receivers.PreNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmPreNotificationScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val dataTimeFormatter: DataTimeFormatter
){

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    suspend fun preNotificationScheduler(alarmId: Int, alarmTime: LocalDateTime){
        val now = LocalDateTime.now()

        if (!alarmTime.isAfter(now)) return

        val preNotificationId = alarmId.toPreNotificationId()
        val idealPreTime = alarmTime.minusMinutes(10)

        //If you have more than 10 minutes before your alarm, set it 10 minutes before.
        //If less than 10 minutes, set it to the current moment (now) so that it appears immediately.
        val actualTriggerTime = if (idealPreTime.isAfter(now)) {
            idealPreTime
        } else {
            now
        }

        val alarm = getAlarmByIdUseCase(alarmId) ?: return
        val isRepeated = alarm.isRepeated


        val intent = Intent(context, PreNotificationReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            putExtra("IS_REPEATED", isRepeated)
            putExtra("ALARM_HOURS", alarm.hours)
            putExtra("ALARM_MINUTES", alarm.minutes)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime= actualTriggerTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        Log.i("ALARM_DEBUG", "set preNotification $alarmId")

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    fun cancelPreNotification(alarmId: Int){
        val preNotificationId = alarmId.toPreNotificationId()
        val intent = Intent(context, PreNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            preNotificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)
    }
}

