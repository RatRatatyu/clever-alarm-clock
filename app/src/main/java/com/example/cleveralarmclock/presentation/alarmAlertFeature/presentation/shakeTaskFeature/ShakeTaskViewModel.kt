package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.usecase.ring.CalculateShakeProgressUseCase
import com.example.cleveralarmclock.core.domain.usecase.ring.StopAlarmPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShakeTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stopAlarmUseCase: StopAlarmPlayerUseCase,
    calculateShakeProgressUseCase: CalculateShakeProgressUseCase
): ViewModel() {

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])
    val progress = calculateShakeProgressUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    private val _isTaskFinished = MutableStateFlow(false)
    val isTaskFinished: StateFlow<Boolean> = _isTaskFinished.asStateFlow()

    init {
        viewModelScope.launch {
            progress.collect { value ->
                if (value >= 1f) {
                    _isTaskFinished.value = true
                    stopAlarmUseCase(alarmId)
                }
            }
        }
    }

    fun stopMusic(){
        viewModelScope.launch {
            stopAlarmUseCase(alarmId)
        }
    }
}