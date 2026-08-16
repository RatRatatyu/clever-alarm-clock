package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.usecase.StopAlarmPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stopAlarmUseCase: StopAlarmPlayerUseCase,
): ViewModel() {

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])



    fun stopMusic(){
        viewModelScope.launch {
            stopAlarmUseCase(alarmId)
        }
    }
}