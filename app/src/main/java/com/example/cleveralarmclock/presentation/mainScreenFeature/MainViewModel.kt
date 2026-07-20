package com.example.cleveralarmclock.presentation.mainScreenFeature

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.data.repository.AlarmRepository
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.service.alarmHandler.domain.AlarmSchedule
import com.example.cleveralarmclock.core.service.alarmHandler.receiver_service.AlarmService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val alarmSchedule: AlarmSchedule,
    val alarmRepository: AlarmRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    val scheduleFlow: Flow<List<AlarmEntity>> = alarmRepository.getAllAlarms()

    fun toggleAlarmStatus(alarm: AlarmEntity){
        viewModelScope.launch {
            alarmRepository.updateAlarm(
                alarm.copy(
                    isActivate = !alarm.isActivate
                )
            )
        }
    }

    fun startAlarm(hour: Int, minute: Int) {
        alarmSchedule.schedule(hour, minute)
    }

    fun stopAlarm(){
        alarmSchedule.cancel()
    }

    fun stopAlarmService() {
        val intent = Intent(context, AlarmService::class.java)
        context.stopService(intent)
    }
}