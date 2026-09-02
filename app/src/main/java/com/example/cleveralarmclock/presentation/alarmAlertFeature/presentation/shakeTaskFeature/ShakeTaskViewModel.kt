package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.usecase.ring.StopAlarmPlayerUseCase
import com.example.cleveralarmclock.core.service.sensors.ShakeDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShakeTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stopAlarmUseCase: StopAlarmPlayerUseCase,
    private val shakeDetector: ShakeDetector
): ViewModel() {

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])


    init {
        shakeDetector.start()
    }

    fun stopMusic(){
        viewModelScope.launch {
            stopAlarmUseCase(alarmId)
            shakeDetector.stop()
        }

    }

    override fun onCleared() {
        super.onCleared()
        shakeDetector.stop()
        Log.i("ALARM_DEBUG", "activity destroyed")
    }

}