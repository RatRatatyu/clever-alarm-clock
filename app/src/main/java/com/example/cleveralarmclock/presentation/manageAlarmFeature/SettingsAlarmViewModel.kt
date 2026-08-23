package com.example.cleveralarmclock.presentation.manageAlarmFeature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.task.AlarmTaskProvider
import com.example.cleveralarmclock.core.domain.task.TaskType
import com.example.cleveralarmclock.core.domain.usecase.manage.AddAlarmUseCase
import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import com.example.cleveralarmclock.core.domain.util.DataTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject


data class SettingAlarmState(
    val selectedHours: Int = 0,
    val selectedMinutes: Int = 0,
    val selectedAmPm: String = "AM",
    val selectedTask: TaskType = TaskType.SHAKE,
    val selectedDayOfWeek: Set<DayOfWeek> = setOf(),
    val isAllDaysSelected: Boolean = false,
    val is24Hours: Boolean = true,
    val isLoading: Boolean = false,
)

@HiltViewModel
class SettingsAlarmViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val dataTimeFormatter: DataTimeFormatter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingAlarmState())
    val uiState: StateFlow<SettingAlarmState> = _uiState.asStateFlow()

    val alarmTaskList = AlarmTaskProvider.allTasks

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])



    init {
        if(alarmId != -1){
            loadAlarm(alarmId)
        }else {
            initializeTime()
        }
    }

    private fun loadAlarm(alarmId: Int){
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val alarm = getAlarmByIdUseCase(alarmId)
            val formater = dataTimeFormatter.convert24To12Hour(alarm?.hours ?: -1)
            val days = alarm?.repeatDays ?: emptySet()

            _uiState.update {
                it.copy(
                    selectedHours = formater.hour,
                    selectedMinutes = alarm?.minutes ?: LocalTime.now().minute,
                    selectedAmPm = formater.amPm,
                    selectedTask = alarm?.taskId ?: TaskType.SHAKE,
                    selectedDayOfWeek = alarm?.repeatDays ?: setOf(),
                    isAllDaysSelected = days.size == 7,
                    is24Hours = formater.is24Format,
                    isLoading = false
                )
            }
        }
    }


    private fun initializeTime() {
        val formater = dataTimeFormatter.convert24To12Hour(-1)

        _uiState.update {
            it.copy(
                selectedHours = formater.hour,
                selectedMinutes = LocalTime.now().minute,
                selectedAmPm = formater.amPm,
                is24Hours = formater.is24Format
            )
        }
    }

    fun onHoursChange(hours: Int) {
        _uiState.update {
            it.copy(
                selectedHours = hours
            )
        }
    }

    fun onMinutesChange(minutes: Int) {
        _uiState.update {
            it.copy(
                selectedMinutes = minutes
            )
        }
    }

    fun onAmPmChange(amPm: String) {
        _uiState.update {
            it.copy(
                selectedAmPm = amPm
            )
        }
    }

    fun onTaskSelected(taskId: TaskType){
        _uiState.update {
            it.copy(
                selectedTask = taskId
            )
        }
    }

    fun selectAllDayOfWeek(){
        _uiState.update { state ->
            val newAllSelectedValue = !state.isAllDaysSelected

            val newSet = if (newAllSelectedValue) {
                DayOfWeek.entries.toSet()
            } else {
                emptySet()
            }

            state.copy(
                selectedDayOfWeek = newSet,
                isAllDaysSelected = newAllSelectedValue
            )
        }
    }

    fun onSelectDayOfWeek(dayOfWeek: DayOfWeek){
        _uiState.update { state ->
            val currentSet = state.selectedDayOfWeek

            val updatedSet = if (currentSet.contains(dayOfWeek)) {
                currentSet - dayOfWeek
            } else {
                currentSet + dayOfWeek
            }

            state.copy(
                selectedDayOfWeek = updatedSet,
                isAllDaysSelected = updatedSet.size == 7
            )
        }
    }

    fun onAddAlarmClock() {
        viewModelScope.launch {
            val formater = dataTimeFormatter.convert12To24Hour(_uiState.value.selectedHours, _uiState.value.selectedAmPm)

            addAlarmUseCase(Alarm(
                id = if (alarmId == -1) 0 else alarmId,
                hours = formater,
                minutes = _uiState.value.selectedMinutes,
                repeatDays = _uiState.value.selectedDayOfWeek,
                isRepeated = _uiState.value.selectedDayOfWeek.isNotEmpty(),
                taskId = _uiState.value.selectedTask
            ))
        }
    }
}