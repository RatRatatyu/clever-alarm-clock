package com.example.cleveralarmclock.core.domain.usecase.schedule

import javax.inject.Inject

class DismissTodayUseCase @Inject constructor(
    private val rescheduleNextAlarmUseCase: RescheduleNextAlarmUseCase,
) {
    suspend operator fun invoke(id: Int) {

        rescheduleNextAlarmUseCase(id, true)

    }
}