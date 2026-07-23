package com.example.cleveralarmclock.core.domain.usecase

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
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
                activeAlarms.minOfOrNull { alarm -> calculateNextDateTime(alarm, now) } ?: return@map null

            val duration = Duration.between(now, nextAlarmDateTime)

            TimeRemaining(
                hours = duration.toHours(),
                minutes = duration.toMinutes() % 60
            )
        }
    }
    private fun calculateNextDateTime(alarm: AlarmEntity, now: LocalDateTime): LocalDateTime {
        var alarmDateTime = now
            .withHour(alarm.hours)
            .withMinute(alarm.minutes)
            .withSecond(0)
            .withNano(0)

        if (alarmDateTime.isBefore(now) || alarmDateTime.isEqual(now)) {
            alarmDateTime = alarmDateTime.plusDays(1)
        }

        return alarmDateTime
    }
}



