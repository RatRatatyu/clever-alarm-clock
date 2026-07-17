package com.example.cleveralarmclock.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleveralarmclock.core.database.dao.AlarmDao
import com.example.cleveralarmclock.core.database.entity.AlarmEntity


@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
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
                   "alarm_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}