package com.example.cleveralarmclock.presentation.manageAlarmFeature

import android.content.Context
import androidx.lifecycle.ViewModel
import android.text.format.DateFormat
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.data.repository.AlarmRepository
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.usecase.AddAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject


data class SettingAlarmState(
    val selectedHours: Int = 0,
    val selectedMinutes: Int = 0,
    val is24Hours: Boolean = true,
    val selectedAmPm: String = "AM"
)

@HiltViewModel
class SettingsAlarmViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingAlarmState())
    val uiState: StateFlow<SettingAlarmState> = _uiState.asStateFlow()


    fun initializeTime(context: Context) {
        val is24Hour = DateFormat.is24HourFormat(context)
        val now = LocalTime.now()

        val selectedMinutes = now.minute
        var selectedHours = now.hour
        var selectedAmPm = "AM"

        if (!is24Hour) {
            val hour12 = now.hour % 12
            selectedHours = if (hour12 == 0) 12 else hour12
            selectedAmPm = if (now.hour < 12) "AM" else "PM"
        }

        _uiState.update {
            it.copy(
                selectedHours = selectedHours,
                selectedMinutes = selectedMinutes,
                selectedAmPm = selectedAmPm,
                is24Hours = is24Hour
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

    fun onAddAlarmClock() {
        viewModelScope.launch {
            addAlarmUseCase(AlarmEntity(
                hours = _uiState.value.selectedHours,
                minutes = _uiState.value.selectedMinutes
            ))
        }
    }
}