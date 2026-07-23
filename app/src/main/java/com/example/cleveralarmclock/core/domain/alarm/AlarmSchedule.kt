package com.example.cleveralarmclock.core.domain.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cleveralarmclock.core.service.receivers.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

interface AlarmSchedule{
    fun schedule(hour: Int, minute: Int, id: Int){}
    fun cancel(id: Int){}
}