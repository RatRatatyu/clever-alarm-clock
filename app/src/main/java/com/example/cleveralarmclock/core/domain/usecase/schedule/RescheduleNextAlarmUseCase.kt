package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import javax.inject.Inject

class RescheduleNextAlarmUseCase @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val alarmRepository: AlarmRepository,
    private val alarmSchedule: AlarmSchedule
){
     suspend operator fun invoke(alarmId: Int, skipForToday: Boolean = false){
         val alarm = getAlarmByIdUseCase(alarmId) ?: return

         if(!alarm.isRepeated){
             val updatedAlarm = alarm.copy(isActivate = false)
             alarmRepository.updateAlarm(updatedAlarm)
         }

         if(alarm.isRepeated){
             alarmSchedule.schedule(
                 alarm.hours,
                 alarm.minutes,
                 alarm.repeatDays,
                 alarm.id,
                 skipForToday
             )
         }else{
             alarmSchedule.cancel(alarm.id)
         }
    }
}