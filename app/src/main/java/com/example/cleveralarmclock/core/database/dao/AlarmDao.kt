package com.example.cleveralarmclock.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cleveralarmclock.core.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: AlarmEntity)

    @Delete
    suspend fun delete(alarm: AlarmEntity)

    @Update
    suspend fun update(alarm: AlarmEntity)

    @Query("DELETE FROM alarmEntity WHERE id = :alarmId")
    suspend fun deleteById(alarmId: Int)

    @Query("SELECT * FROM alarmEntity")
    fun getAllAlarmClock(): Flow<List<AlarmEntity>>


}