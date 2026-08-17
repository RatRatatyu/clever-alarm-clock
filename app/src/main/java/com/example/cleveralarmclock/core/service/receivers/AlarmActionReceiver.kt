package com.example.cleveralarmclock.core.service.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cleveralarmclock.core.domain.usecase.DisableAlarmUseCase
import com.example.cleveralarmclock.core.domain.usecase.DismissTodayUseCase
import com.example.cleveralarmclock.core.domain.util.toPreNotificationId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class AlarmActionReceiver: BroadcastReceiver()  {

    @Inject
    lateinit var disableAlarmUseCase: DisableAlarmUseCase

    @Inject
    lateinit var dismissTodayUseCase: DismissTodayUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return

        val alarmId = intent?.getIntExtra("ALARM_ID", -1) ?: -1
        if (alarmId == -1) return

        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        alarmId?.let { notificationManager.cancel(it.toPreNotificationId()) }

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (intent?.action) {
                    ACTION_DISMISS_TODAY -> dismissTodayUseCase(alarmId)
                    ACTION_TURN_OFF_PERMANENTLY -> disableAlarmUseCase(alarmId)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_DISMISS_TODAY = "com.example.cleveralarmclock.ACTION_DISMISS_TODAY"
        const val ACTION_TURN_OFF_PERMANENTLY = "com.example.cleveralarmclock.ACTION_TURN_OFF_PERMANENTLY"
    }
}