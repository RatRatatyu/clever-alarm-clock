package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cleveralarmclock.core.domain.alarm.AlarmPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val alarmPlayer: AlarmPlayer
): ViewModel(){

    val alarmId: Int = savedStateHandle.get<Int>("ALARM_ID") ?: -1

    fun stopMusic(){
        alarmPlayer.stopPlayer()
    }

}