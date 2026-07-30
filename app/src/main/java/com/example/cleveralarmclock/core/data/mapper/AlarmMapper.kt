package com.example.cleveralarmclock.core.data.mapper

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity

import com.example.cleveralarmclock.core.domain.module.Alarm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmMapper @Inject constructor(){
    fun AlarmEntity.toDomain(): Alarm {
        return Alarm(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            colorHex = colorHex,
            isActivate = this.isActivate
        )
    }

    fun Alarm.toEntity(): AlarmEntity{
        return AlarmEntity(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            colorHex = colorHex,
            isActivate = this.isActivate
        )
    }

    fun List<AlarmEntity>.toDomainList(): List<Alarm>{
        return this.map { it.toDomain() }
    }
}