package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.alarmRingFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.task.TaskType
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.usecase.ring.SnoozeAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AlarmRingUiState(
    val isLoading: Boolean = false,
    val taskId: TaskType = TaskType.SHAKE

)

@HiltViewModel
class AlarmRingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val snoozeAlarmUseCase: SnoozeAlarmUseCase
): ViewModel() {

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])

    private val _uiState = MutableStateFlow(AlarmRingUiState())
    val uiState: StateFlow<AlarmRingUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TaskType>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _closeActivityEvent = Channel<Unit>()
    val closeActivityEvent = _closeActivityEvent.receiveAsFlow()

    init {
        loadAlarmInfo()
    }

    private fun loadAlarmInfo(){
        _uiState.update { it.copy(
            isLoading = true
        ) }
        viewModelScope.launch {
            try {
                if(alarmId != -1){
                    val alarm = getAlarmByIdUseCase(alarmId)
                    _uiState.update { it.copy(
                        taskId = alarm?.taskId ?: TaskType.SHAKE
                    ) }
                }
            } finally {
                _uiState.update { it.copy(
                    isLoading = false
                ) }
            }
        }
    }

    fun onStopAlarm(){
        viewModelScope.launch {
            _navigationEvent.send(_uiState.value.taskId)
        }
    }

    fun snoozeAlarm(){
        viewModelScope.launch {
            snoozeAlarmUseCase(alarmId)
            _closeActivityEvent.send(Unit)
        }
    }

}