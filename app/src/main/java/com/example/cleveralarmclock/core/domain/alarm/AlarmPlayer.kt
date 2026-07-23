package com.example.cleveralarmclock.core.domain.alarm

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.cleveralarmclock.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AlarmPlayer {
    fun registrationPlayer() {}
    fun startPlayer() {}
    fun stopPlayer() {}
}