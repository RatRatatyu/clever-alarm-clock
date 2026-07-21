package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.usecase.GetAlarmsUseCase
import com.example.cleveralarmclock.core.domain.usecase.ToggleAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase
): ViewModel() {

    val scheduleFlow: Flow<List<AlarmEntity>> = getAlarmsUseCase()

    fun toggleAlarmStatus(alarm: AlarmEntity){
        viewModelScope.launch {
            toggleAlarmUseCase(alarm)
        }
    }

}