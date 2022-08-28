package com.example.nasapictureapp.networking.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val builder = GsonBuilder().disableHtmlEscaping().create()
        return GsonConverterFactory.create(builder)
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        factory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }
    @Provides
    fun provideGson() : Gson = GsonBuilder().create()


}