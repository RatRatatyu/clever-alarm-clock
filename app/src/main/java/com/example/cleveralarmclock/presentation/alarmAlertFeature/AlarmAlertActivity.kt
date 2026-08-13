package com.example.cleveralarmclock.presentation.alarmAlertFeature

import android.app.KeyguardManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.navigation.AlarmNavHost
import com.example.cleveralarmclock.ui.theme.CleverAlarmClockTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmAlertActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        val keyguardManager = getSystemService(KeyguardManager::class.java)
        keyguardManager?.requestDismissKeyguard(this, null)

        val alarmId = intent.getIntExtra("ALARM_ID", -1)

        enableEdgeToEdge()
        setContent {
            CleverAlarmClockTheme {
                AlarmNavHost(alarmId = alarmId)
            }
        }
    }
}