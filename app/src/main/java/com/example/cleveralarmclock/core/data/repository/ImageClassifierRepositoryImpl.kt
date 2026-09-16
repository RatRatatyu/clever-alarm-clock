package com.example.cleveralarmclock.core.data.repository

import android.graphics.Bitmap
import com.example.cleveralarmclock.core.data.remote.datasource.RemoteDataSource
import com.example.cleveralarmclock.core.data.remote.dto.ClassificationResponseDto
import com.example.cleveralarmclock.core.domain.repository.ImageClassifierRepository
import javax.inject.Inject

class ImageClassifierRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): ImageClassifierRepository {

    override suspend fun classifyImage(image: Bitmap): List<ClassificationResponseDto> {
        return remoteDataSource.classify(image = image)
    }
}