package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.usecase.GetAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.GetNextAlarmTimeUseCase
import com.example.cleveralarmclock.core.domain.usecase.ToggleAlarmUseCase
import com.example.cleveralarmclock.presentation.mainScreenFeature.util.TimeRemainingFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase,
    private val getNextAlarmTimeUseCase: GetNextAlarmTimeUseCase,
    private val timeRemainingFormatter: TimeRemainingFormatter
): ViewModel() {


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

    fun toggleAlarmStatus(alarm: AlarmEntity){
        viewModelScope.launch {
            toggleAlarmUseCase(alarm)
        }
    }



}