package com.example.nasapictureapp.features.picture.di

import com.example.nasapictureapp.features.picture.data.repository.PictureInterface
import com.example.nasapictureapp.features.picture.data.repository.PictureRepository
import com.example.nasapictureapp.util.InternetConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GridModule {

    @Provides
    fun provideRepository(internetConnectivity: InternetConnectivity): PictureInterface =
        PictureRepository(internetConnectivity)
}
