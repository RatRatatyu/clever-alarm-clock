package com.example.cleveralarmclock.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cleveralarmclock.core.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: AlarmEntity): Long

    @Query("DELETE FROM alarmEntity WHERE id IN (:ids)")
    suspend fun deleteAlarmsByIds(ids: List<Int>)

    @Update
    suspend fun update(alarm: AlarmEntity)

    @Query("SELECT * FROM alarmEntity WHERE isActivate = 1")
    fun getActiveAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarmEntity ORDER BY id DESC")
    fun getAllAlarmClock(): Flow<List<AlarmEntity>>
}