package com.example.cleveralarmclock.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ShakeRepository {

    fun getShakeEvents(): Flow<Unit>
}