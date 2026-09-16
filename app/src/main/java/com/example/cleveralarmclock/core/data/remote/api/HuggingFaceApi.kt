package com.example.cleveralarmclock.core.data.remote.api

import com.example.cleveralarmclock.core.data.remote.dto.ClassificationResponseDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface HuggingFaceApi {
    @POST
    suspend fun classifyImage(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): List<ClassificationResponseDto>
}