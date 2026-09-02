package com.example.cleveralarmclock.core.service.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.sqrt

class ShakeDetector @Inject constructor(
    @param:ApplicationContext private val context: Context
): SensorEventListener {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var lastTimeShaken = 0L

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        Log.i("ALARM_DEBUG", "shale detector start")
    }

    fun stop() {
        sensorManager.unregisterListener(this)
        Log.i("ALARM_DEBUG", "Shake Detector stop")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = sqrt(x * x + y * y + z * z) / SensorManager.GRAVITY_EARTH

            if (acceleration > SHAKE_THRESHOLD) {
                val now = System.currentTimeMillis()

                if(now - lastTimeShaken >= SHAKE_SLOP_TIME_MS){
                    lastTimeShaken = now
                    Log.d("ALARM_DEBUG", "Device shaken!")
                }

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    companion object{
        private const val SHAKE_THRESHOLD = 2.7f
        private const val SHAKE_SLOP_TIME_MS = 300
    }


}
