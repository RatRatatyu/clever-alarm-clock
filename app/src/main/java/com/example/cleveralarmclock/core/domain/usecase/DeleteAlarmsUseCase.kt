package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule
) {
    suspend operator fun invoke(alarms: List<Int>){
        alarms.forEach { id ->
            alarmSchedule.cancel(id)
        }
        alarmRepository.deleteAlarmsByIds(alarms)
    }
}