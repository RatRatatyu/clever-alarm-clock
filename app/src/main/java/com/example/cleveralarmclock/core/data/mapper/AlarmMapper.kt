package com.example.cleveralarmclock.core.data.mapper

import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity

import com.example.cleveralarmclock.core.domain.module.AlarmModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmMapper @Inject constructor(){
    fun AlarmEntity.toDomain(): AlarmModel {
        return AlarmModel(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            colorHex = colorHex,
            isActivate = this.isActivate
        )
    }

    fun AlarmModel.toEntity(): AlarmEntity{
        return AlarmEntity(
            id = this.id,
            hours = this.hours,
            minutes = this.minutes,
            colorHex = colorHex,
            isActivate = this.isActivate
        )
    }

    fun List<AlarmEntity>.toDomainList(): List<AlarmModel>{
        return this.map { it.toDomain() }
    }
}