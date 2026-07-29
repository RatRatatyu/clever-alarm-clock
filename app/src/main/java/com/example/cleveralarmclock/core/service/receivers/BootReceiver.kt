package com.example.cleveralarmclock.core.service.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.cleveralarmclock.core.service.worker.RescheduleAlarmsWorker

class BootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx = context ?: return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED ||
            intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED ||
            intent?.action == "com.example.cleveralarmclock.TEST_BOOT"
            ){

            val rescheduleAlarmWorker: WorkRequest = OneTimeWorkRequestBuilder<RescheduleAlarmsWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()

            WorkManager
                .getInstance(ctx)
                .enqueue(rescheduleAlarmWorker)
        }

    }
}