package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.alarmRingFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.task.AlarmTaskProvider
import com.example.cleveralarmclock.core.domain.task.TaskType
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.usecase.ring.SnoozeAlarmUseCase
import com.example.cleveralarmclock.core.domain.util.DataTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime


data class AlarmRingUiState(
    val isLoading: Boolean = false,
    val currentTime: String = "00:00",
    val amPmValue: String = "AM",
    val is24Format: Boolean = true,
    val taskId: TaskType = TaskType.SHAKE,
    val taskName: Int? = null

)

@HiltViewModel
class AlarmRingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val snoozeAlarmUseCase: SnoozeAlarmUseCase,
    private val dataTimeFormatter: DataTimeFormatter
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
                    val taskName = AlarmTaskProvider.getTaskById(alarm?.taskId?.id ?: TaskType.SHAKE.id)
                    val formatter = dataTimeFormatter.convert24To12Hour(LocalDateTime.now().hour)
                    val timeFormatter = String.format(java.util.Locale.getDefault(), "%02d:%02d", formatter.hour,
                        alarm?.minutes
                    )



                    _uiState.update { it.copy(
                        currentTime = timeFormatter,
                        amPmValue = formatter.amPm,
                        is24Format = formatter.is24Format,
                        taskId = alarm?.taskId ?: TaskType.SHAKE,
                        taskName = taskName?.titleResId
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