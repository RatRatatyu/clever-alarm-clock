package com.example.cleveralarmclock.core.domain.repository

import android.graphics.Bitmap
import com.example.cleveralarmclock.core.data.remote.dto.ClassificationResponseDto

interface ImageClassifierRepository {
    suspend fun classifyImage(image: Bitmap): List<ClassificationResponseDto>
}