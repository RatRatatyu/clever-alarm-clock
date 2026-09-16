package com.example.cleveralarmclock.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ClassificationResponseDto(
    @SerializedName("label") val label: String,
    @SerializedName("score") val score: Float
)