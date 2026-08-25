package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import javax.inject.Inject

class RescheduleNextAlarmUseCase @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val alarmSchedule: AlarmSchedule,
    private val alarmRepository: AlarmRepository
){
     suspend operator fun invoke(alarmId: Int){
         val alarm = getAlarmByIdUseCase(alarmId) ?: return

         if(!alarm.isRepeated){
             val updatedAlarm = alarm.copy(isActivate = false)
             alarmRepository.updateAlarm(updatedAlarm)
             alarmSchedule.cancel(alarmId)
             return
         }

         val triggerTime = AlarmTimeCalculator.calculateNextTriggerTime(
             alarm.hours,
             alarm.minutes,
             alarm.repeatDays,
             false
         )

         alarmSchedule.schedule(alarm.id, triggerTime)


    }
}