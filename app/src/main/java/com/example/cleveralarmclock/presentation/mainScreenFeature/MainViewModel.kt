package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.usecase.DeleteAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.GetAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.GetNextAlarmTimeUseCase
import com.example.cleveralarmclock.core.domain.usecase.ToggleAlarmUseCase
import com.example.cleveralarmclock.core.domain.util.DataTimeFormatter
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
import java.time.DayOfWeek
import java.util.Locale
import javax.inject.Inject


data class MainState(
    val isSelectedMode: Boolean = false,
    val selectedList: List<Int> = listOf(),
    val allSelected: Boolean = false
)

data class AlarmUiModel(
    val id: Int,
    val timeFormatted: String,
    val isActivated: Boolean,
    val repeatedDays: Set<DayOfWeek>
)
@HiltViewModel
class MainViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase,
    getNextAlarmTimeUseCase: GetNextAlarmTimeUseCase,
    private val timeRemainingFormatter: TimeRemainingFormatter,
    private val deleteAlarmsUseCase: DeleteAlarmsUseCase,
    private val dataTimeFormatter: DataTimeFormatter
): ViewModel() {


    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState.asStateFlow()
    private val _navigationEvent = Channel<Int>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val scheduleFlow: StateFlow<List<AlarmUiModel>> = getAlarmsUseCase()
        .map { list ->
            list.map { alarm ->
                val formatted = dataTimeFormatter.convert24To12Hour(alarm.hours)

                val timeString = if (formatted.is24Format) {
                    String.format(Locale.getDefault(), "%02d:%02d", formatted.hour, alarm.minutes)
                } else {
                    String.format(Locale.getDefault(), "%02d:%02d %s", formatted.hour, alarm.minutes, formatted.amPm)
                }

                AlarmUiModel(
                    id = alarm.id,
                    timeFormatted = timeString,
                    isActivated = alarm.isActivate,
                    repeatedDays = alarm.repeatDays,
                )
            }
        }
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



    fun onPress(alarmId: Int){
        if (_uiState.value.isSelectedMode){
            onAddAlarmToList(alarmId)
        }else{
            viewModelScope.launch {
                _navigationEvent.send(alarmId)
            }
        }
    }

    fun deleteAlarms(){
        viewModelScope.launch {
            deleteAlarmsUseCase(_uiState.value.selectedList)
            clearSelection()
        }
    }

    fun onLongPress(alarmId: Int) {
        if (!_uiState.value.isSelectedMode) {
            _uiState.update {
                it.copy(isSelectedMode = true, selectedList = listOf(alarmId))
            }
        }
    }

    fun getAllChecked(){
        _uiState.update { state ->
            val newAllSelectedValue = !state.allSelected

            val newList = if (newAllSelectedValue) {
                scheduleFlow.value.map { it.id }
            } else {
                emptyList()
            }

            state.copy(
                selectedList = newList,
                allSelected = newAllSelectedValue
            )
        }
    }

    fun clearSelection() {
        _uiState.update { MainState() }
    }

    fun toggleAlarmStatus(alarmId: Int){
        viewModelScope.launch {
            toggleAlarmUseCase(alarmId)
        }
    }

    private fun onAddAlarmToList(alarmId: Int){
        _uiState.update { state ->
            val newSelected = if (state.selectedList.contains(alarmId)) {
                state.selectedList - alarmId
            } else {
                state.selectedList + alarmId
            }

            state.copy(
                selectedList = newSelected,
                isSelectedMode = newSelected.isNotEmpty(),
                allSelected = newSelected.isNotEmpty() && newSelected.size == scheduleFlow.value.size
            )
        }
    }
}