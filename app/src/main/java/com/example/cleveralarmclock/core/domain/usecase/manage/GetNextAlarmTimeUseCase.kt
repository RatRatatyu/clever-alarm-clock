package com.example.cleveralarmclock.core.domain.usecase.manage

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.domain.util.AlarmTimeCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

data class TimeRemaining(
    val hours: Long,
    val minutes: Long
)

class GetNextAlarmTimeUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(): Flow<TimeRemaining?> {
        return alarmRepository.getActiveAlarms().map { activeAlarms ->
            if (activeAlarms.isEmpty()) return@map null

            val now = LocalDateTime.now()

            val nextAlarmDateTime =
                activeAlarms.minOfOrNull { alarm ->
                    AlarmTimeCalculator.calculateNextTriggerTime(alarm.hours, alarm.minutes, alarm.repeatDays) }
                    ?: return@map null

            val duration = Duration.between(now, nextAlarmDateTime)

            TimeRemaining(
                hours = duration.toHours(),
                minutes = duration.toMinutes() % 60
            )
        }
    }
}



