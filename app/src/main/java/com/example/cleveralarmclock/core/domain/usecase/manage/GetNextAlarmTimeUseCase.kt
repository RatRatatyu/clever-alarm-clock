package com.example.cleveralarmclock.core.domain.usecase.manage

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

data class TimeRemaining(
    val hours: Long,
    val minutes: Long
)

class GetNextAlarmTimeUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    private fun tickerFlow(period: kotlin.time.Duration, initialDelay: kotlin.time.Duration = kotlin.time.Duration.ZERO): Flow<Unit> = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    operator fun invoke(): Flow<TimeRemaining?> {
        return combine(
            alarmRepository.getActiveAlarms(),
            tickerFlow(60.seconds)
        ) { activeAlarms, _ ->
            if (activeAlarms.isEmpty()) return@combine null

            val now = LocalDateTime.now()

            val nextAlarmDateTime = activeAlarms.minOfOrNull { alarm ->
                AlarmTimeCalculator.calculateNextTriggerTime(
                    hour = alarm.hours,
                    minute = alarm.minutes,
                    repeatDays = alarm.repeatDays,
                    skipForToday = alarm.lastDismissed == LocalDate.now()
                )
            } ?: return@combine null

            val duration = Duration.between(now, nextAlarmDateTime)

            TimeRemaining(
                hours = duration.toHours(),
                minutes = duration.toMinutes() % 60
            )
        }
    }
}
