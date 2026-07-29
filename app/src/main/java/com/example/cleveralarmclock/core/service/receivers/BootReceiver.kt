package com.example.cleveralarmclock.core.service.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.cleveralarmclock.core.service.worker.RescheduleAlarmsWorker

class BootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED ||
            intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED
            ){

            val rescheduleAlarmWorker: WorkRequest = OneTimeWorkRequestBuilder<RescheduleAlarmsWorker>().build()
            WorkManager
                .getInstance(ctx)
                .enqueue(rescheduleAlarmWorker)
        }

    }
}