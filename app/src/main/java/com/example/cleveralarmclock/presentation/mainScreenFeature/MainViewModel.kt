package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.usecase.DeleteAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.GetAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.GetNextAlarmTimeUseCase
import com.example.cleveralarmclock.core.domain.usecase.ToggleAlarmUseCase
import com.example.cleveralarmclock.presentation.mainScreenFeature.util.TimeRemainingFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MainState(
    val isSelectedMode: Boolean = false,
    val selectedList: List<Int> = listOf()
)
@HiltViewModel
class MainViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase,
    getNextAlarmTimeUseCase: GetNextAlarmTimeUseCase,
    private val timeRemainingFormatter: TimeRemainingFormatter,
    private val deleteAlarmsUseCase: DeleteAlarmsUseCase
): ViewModel() {


    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState.asStateFlow()
    private val _navigationEvent = Channel<Int>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val scheduleFlow: StateFlow<List<AlarmEntity>> = getAlarmsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val nextAlarmTime: StateFlow<String?> = getNextAlarmTimeUseCase()
        .map { timeRemaining ->
            if (timeRemaining == null) {
                null
            } else {
                timeRemainingFormatter.format(
                    hours = timeRemaining.hours,
                    minutes = timeRemaining.minutes
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )



    fun onPress(alarm: AlarmEntity){
        if (_uiState.value.isSelectedMode){
            onAddAlarmToList(alarm)
        }else{
            viewModelScope.launch {
                _navigationEvent.send(alarm.id)
            }
        }
    }

    fun deleteAlarms(){
        viewModelScope.launch {
            deleteAlarmsUseCase(_uiState.value.selectedList)
            clearSelection()
        }
    }

    fun onLongPress(alarm: AlarmEntity){
        _uiState.update { state ->
            if (!state.isSelectedMode) {
                state.copy(
                    isSelectedMode = true,
                    selectedList = listOf(alarm.id)
                )
            } else {
                state
            }
        }
    }

    fun clearSelection() {
        _uiState.update { MainState() }
    }

    fun toggleAlarmStatus(alarm: AlarmEntity){
        viewModelScope.launch {
            toggleAlarmUseCase(alarm)
        }
    }

    private fun onAddAlarmToList(alarm: AlarmEntity){
        _uiState.update { state ->
            val newSelected = if (state.selectedList.contains(alarm.id)) {
                state.selectedList - alarm.id
            } else {
                state.selectedList + alarm.id
            }

            state.copy(
                selectedList = newSelected,
                isSelectedMode = newSelected.isNotEmpty()
            )
        }
    }



}