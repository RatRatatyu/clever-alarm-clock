package com.example.cleveralarmclock.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleveralarmclock.core.data.database.converters.AlarmConverters
import com.example.cleveralarmclock.core.data.database.dao.AlarmDao
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity


@Database(entities = [AlarmEntity::class], version = 2, exportSchema = false)
@TypeConverters(AlarmConverters::class)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var Instance: AlarmDatabase? = null

        fun getDatabase(context: Context): AlarmDatabase {
           return Instance ?: synchronized(this) {
               Room.databaseBuilder(
                   context.applicationContext,
                   AlarmDatabase::class.java,
                   "alarm_database"
               )
                   .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}