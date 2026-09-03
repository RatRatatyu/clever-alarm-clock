package com.example.cleveralarmclock.core.di

import com.example.cleveralarmclock.core.domain.repository.AlarmRepository
import com.example.cleveralarmclock.core.data.repository.AlarmRepositoryImpl
import com.example.cleveralarmclock.core.data.repository.ShakeRepositoryImpl
import com.example.cleveralarmclock.core.domain.repository.ShakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(
        alarmRepositoryImpl: AlarmRepositoryImpl
    ): AlarmRepository

    @Binds
    @Singleton
    abstract fun bindShakeRepository(
        shakeRepositoryImpl: ShakeRepositoryImpl
    ): ShakeRepository
}