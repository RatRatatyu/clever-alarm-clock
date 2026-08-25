package com.example.cleveralarmclock.core.service.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.schedule.ScheduleAlarmUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RescheduleAlarmsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Log.i("ALARM_DEBUG", "Alarms rescheduled started!")

        return try {
            val activeAlarms = alarmRepository.getActiveAlarms().first()

            activeAlarms.forEach { alarm ->
               scheduleAlarmUseCase(alarm)
            }

            Log.i("ALARM_DEBUG", "Successfully rescheduled ${activeAlarms.size} alarms.")
            Result.success()
        } catch (e: Exception) {
            Log.e("ALARM_DEBUG", "Error rescheduling alarms", e)
            Result.retry()
        }
    }
}