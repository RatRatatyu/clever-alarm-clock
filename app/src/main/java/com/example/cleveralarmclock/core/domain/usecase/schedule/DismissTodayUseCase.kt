package com.example.cleveralarmclock.core.domain.usecase.schedule

import com.example.cleveralarmclock.core.domain.usecase.manage.GetAlarmByIdUseCase
import javax.inject.Inject

class DismissTodayUseCase @Inject constructor(
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase
) {
    suspend operator fun invoke(id: Int) {
        val alarm = getAlarmByIdUseCase(id) ?: return

        scheduleAlarmUseCase(alarm, true)

    }
}