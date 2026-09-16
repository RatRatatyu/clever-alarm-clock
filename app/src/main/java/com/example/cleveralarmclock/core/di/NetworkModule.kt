package com.example.cleveralarmclock.core.di

import com.example.cleveralarmclock.core.data.remote.api.HuggingFaceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://router.huggingface.co/hf-inference/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHuggingFaceApi(retrofit: Retrofit): HuggingFaceApi {
        return retrofit.create(HuggingFaceApi::class.java)
    }
}