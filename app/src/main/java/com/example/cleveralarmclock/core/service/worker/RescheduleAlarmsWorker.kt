package com.example.cleveralarmclock.core.service.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RescheduleAlarmsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Log.i("ALARM_DEBUGING", "Alarms rescheduled started!")

        return try {
            val activeAlarms = alarmRepository.getActiveAlarms().first()

            activeAlarms.forEach { alarm ->
                alarmSchedule.schedule(
                    id = alarm.id,
                    hour = alarm.hours,
                    repeatDays =  alarm.repeatDays,
                    minute = alarm.minutes
                )
            }
            Log.i("ALARM_DEBUGING", "Successfully rescheduled ${activeAlarms.size} alarms.")
            Result.success()
        } catch (e: Exception) {
            Log.e("ALARM_DEBUGING", "Error rescheduling alarms", e)
            Result.retry()
        }
    }
}