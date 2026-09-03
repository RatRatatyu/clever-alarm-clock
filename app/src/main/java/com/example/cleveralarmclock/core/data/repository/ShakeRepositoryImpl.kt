package com.example.cleveralarmclock.core.data.repository

import com.example.cleveralarmclock.core.domain.repository.ShakeRepository
import com.example.cleveralarmclock.core.service.sensors.ShakeDetector
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShakeRepositoryImpl @Inject constructor(
    private val shakeDetector: ShakeDetector
): ShakeRepository {

    override fun getShakeEvents(): Flow<Unit> = callbackFlow {
        shakeDetector.start()

        val job = launch {
            shakeDetector.shakeEvent.collect {
                trySend(it)
            }
        }

        awaitClose {
            job.cancel()
            shakeDetector.stop()
        }
    }

}