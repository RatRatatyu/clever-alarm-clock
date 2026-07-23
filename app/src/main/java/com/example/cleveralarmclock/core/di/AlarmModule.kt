package com.example.cleveralarmclock.core.di

import com.example.cleveralarmclock.core.domain.alarm.AlarmPlayer
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.service.alarm.AlarmPlayerImpl
import com.example.cleveralarmclock.core.service.alarm.AlarmScheduleImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {

    @Binds
    @Singleton
    abstract fun bindAlarmPlayer(
        alarmPlayerImpl: AlarmPlayerImpl
    ) : AlarmPlayer

    @Binds
    @Singleton
    abstract  fun bindAlarmSchedule(
        alarmScheduleImpl: AlarmScheduleImpl
    ): AlarmSchedule


}