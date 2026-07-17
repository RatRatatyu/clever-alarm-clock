package com.example.cleveralarmclock.core.di

import android.content.Context
import androidx.room.Room
import com.example.cleveralarmclock.core.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            "alarm_db"
        ).build()
    }

    @Provides
    fun provideAlarmDao(db: AlarmDatabase) = db.alarmDao()
}