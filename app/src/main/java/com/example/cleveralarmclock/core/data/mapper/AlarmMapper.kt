package com.example.cleveralarmclock.core.data.mapper

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.module.Alarm
import com.example.cleveralarmclock.core.domain.task.TaskType
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmMapper @Inject constructor() {

    fun AlarmEntity.toDomain(): Alarm {
        val taskType = TaskType.entries.find { it.id == this.taskId } ?: TaskType.SHAKE

        return Alarm(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            taskId = taskType,
            repeatDays = this.repeatDays,
            isRepeated = this.isRepeated,
            isActivate = this.isActivate,
            lastDismissed = this.lastDismissed
        )
    }

    fun Alarm.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            taskId = this.taskId.id,
            repeatDays = this.repeatDays,
            isRepeated = this.isRepeated,
            isActivate = this.isActivate,
            lastDismissed = this.lastDismissed
        )
    }

    fun List<AlarmEntity>.toDomainList(): List<Alarm> {
        return this.map { it.toDomain() }
    }
}