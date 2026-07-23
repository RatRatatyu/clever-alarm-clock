package com.example.cleveralarmclock.core.service.alarm

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.domain.alarm.AlarmPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmPlayerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmPlayer {

    private var exoPlayer: ExoPlayer? = null
    override fun registrationPlayer() {
        if (exoPlayer != null) return

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_ALARM)
            .setContentType(C.AUDIO_CONTENT_TYPE_SONIFICATION)
            .build()


        exoPlayer = ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, false)
            .build()
    }

    override fun startPlayer() {
        Log.i("ALARM_DEBUGER", "start music")

        if (exoPlayer == null) {
            registrationPlayer()
        }

        val rawUri = "android.resource://${context.packageName}/${R.raw.alarm_classic}".toUri()
        val mediaItem = MediaItem.fromUri(rawUri)

        exoPlayer?.apply {
            setMediaItem(mediaItem)
            repeatMode = ExoPlayer.REPEAT_MODE_ONE
            prepare()
            play()
        }
    }

    override fun stopPlayer() {
        Log.i("ALARM_DEBUGER", "stop music")
        exoPlayer?.apply {
            stop()
            release()
        }
        exoPlayer = null
    }
}