package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.usecase.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.usecase.StopAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stopAlarmUseCase: StopAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase
): ViewModel(){

    val alarmId: Int = savedStateHandle.get<Int>("ALARM_ID") ?: -1



    fun stopMusic(){
        viewModelScope.launch {
            val alarmById = getAlarmByIdUseCase(alarmId)
            stopAlarmUseCase(alarmById!!)
        }

    }

}