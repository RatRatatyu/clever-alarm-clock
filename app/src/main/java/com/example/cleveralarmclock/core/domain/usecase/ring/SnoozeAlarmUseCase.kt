package com.example.cleveralarmclock.core.domain.usecase.ring

import android.content.Context
import android.content.Intent
import com.example.cleveralarmclock.core.domain.alarm.AlarmPlayer
import com.example.cleveralarmclock.core.domain.alarm.AlarmSchedule
import com.example.cleveralarmclock.core.service.services.AlarmService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SnoozeAlarmUseCase @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val alarmPlayer: AlarmPlayer,
    private val alarmSchedule: AlarmSchedule
) {
    suspend operator fun invoke(alarmId: Int){
        alarmPlayer.stopPlayer()

        val intent = Intent(context, AlarmService::class.java)
        context.stopService(intent)

        alarmSchedule.snoozeFor10seconds(alarmId)
    }
}