package com.example.cleveralarmclock.presentation.mainScreenFeature.data

import androidx.lifecycle.ViewModel
import com.example.cleveralarmclock.core.service.alarmHandler.AlarmSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    val alarmSchedule: AlarmSchedule
): ViewModel(){
    fun startAlarm(){
        alarmSchedule.schedule()
    }

    fun cancelAlarm(){
        alarmSchedule.cancel()
    }
}