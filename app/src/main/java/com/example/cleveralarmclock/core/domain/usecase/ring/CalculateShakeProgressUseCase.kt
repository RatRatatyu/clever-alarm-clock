package com.example.cleveralarmclock.core.domain.usecase.ring

import com.example.cleveralarmclock.core.domain.repository.ShakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import javax.inject.Inject

class CalculateShakeProgressUseCase @Inject constructor(
    private val shakeRepository: ShakeRepository
) {

    operator fun invoke(): Flow<Float> {

        return shakeRepository.getShakeEvents()
            .scan(0) { count, _ -> count + 1 }
            .map { it / 10f }
            .takeWhile { it <= 1.0f }
    }
}
