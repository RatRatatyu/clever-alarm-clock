package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import javax.inject.Inject

class DismissTodayUseCase @Inject constructor(
    private val rescheduleNextAlarmUseCase: RescheduleNextAlarmUseCase,
    private val alarmSchedule: AlarmSchedule
) {
    suspend operator fun invoke(id: Int) {
        rescheduleNextAlarmUseCase(id)
        alarmSchedule.cancel(id)
    }
}