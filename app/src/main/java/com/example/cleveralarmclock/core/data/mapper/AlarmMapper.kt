package com.example.cleveralarmclock.core.data.mapper

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import com.example.cleveralarmclock.core.domain.module.Alarm
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmMapper @Inject constructor(){
    fun AlarmEntity.toDomain(): Alarm {
        val repeatDays = if (this.repeatDays.isEmpty()) emptySet() else
        this.repeatDays.split(",").map { DayOfWeek.of(it.toInt()) }.toSet()

        return Alarm(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            taskId = this.taskId,
            repeatDays = repeatDays,
            isRepeated = this.isRepeated,
            isActivate = this.isActivate
        )
    }

    fun Alarm.toEntity(): AlarmEntity{
        return AlarmEntity(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            taskId = this.taskId,
            repeatDays = this.repeatDays.joinToString(",") { it.value.toString() },
            isRepeated = this.isRepeated,
            isActivate = this.isActivate
        )
    }

    fun List<AlarmEntity>.toDomainList(): List<Alarm>{
        return this.map { it.toDomain() }
    }
}