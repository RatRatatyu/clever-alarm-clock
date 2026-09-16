package com.example.cleveralarmclock.core.data.remote.datasource

import android.graphics.Bitmap
import android.util.Log
import com.example.cleveralarmclock.BuildConfig
import com.example.cleveralarmclock.core.data.remote.api.HuggingFaceApi
import com.example.cleveralarmclock.core.data.remote.dto.ClassificationResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun classify(image: Bitmap): List<ClassificationResponseDto>
}

class RemoteDataSourceImpl @Inject constructor(
    private val api: HuggingFaceApi
) : RemoteDataSource {

    private val token = BuildConfig.HF_TOKEN

    override suspend fun classify(image: Bitmap): List<ClassificationResponseDto> {
        Log.i("NETWORK_DEBUG", "Sending image to HuggingFace...")

        val imageByteArray = compressBitmap(image)
        val requestBody = imageByteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())

        return api.classifyImage(
            url = "models/google/vit-base-patch16-224",
            token = "Bearer $token",
            body = requestBody
        )
    }

    private suspend fun compressBitmap(bitmap: Bitmap): ByteArray = withContext(Dispatchers.IO) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.toByteArray()
    }
}