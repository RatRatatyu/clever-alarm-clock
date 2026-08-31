package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import java.time.LocalDate
import javax.inject.Inject

class DismissTodayUseCase @Inject constructor(
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(id: Int) {
       val alarm = getAlarmByIdUseCase(id) ?: return

       val updatedAlarm =  alarm.copy(lastDismissed = LocalDate.now())

       alarmRepository.updateAlarm(updatedAlarm)
       scheduleAlarmUseCase(updatedAlarm, true)

    }
}