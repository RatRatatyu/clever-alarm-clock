package com.example.cleveralarmclock.presentation.mainScreenFeature.data

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.cleveralarmclock.core.service.alarmHandler.domain.AlarmSchedule
import com.example.cleveralarmclock.core.service.alarmHandler.receiver_service.AlarmService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    val alarmSchedule: AlarmSchedule,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
): ViewModel() {

    fun startAlarm() {
        alarmSchedule.schedule()
    }

    fun stopAlarm(){
        alarmSchedule.cancel()
    }

    fun stopAlarmService() {
        val intent = Intent(context, AlarmService::class.java)
        context.stopService(intent)
    }
}