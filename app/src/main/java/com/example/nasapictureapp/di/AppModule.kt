package com.example.nasapictureapp.di

import android.app.Application
import android.content.Context
import com.example.nasapictureapp.util.InternetConnectivity
import com.example.nasapictureapp.util.InternetConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideInternetConnectivity(context: Context): InternetConnectivity {
        return InternetConnectivityImpl(context)
    }

}