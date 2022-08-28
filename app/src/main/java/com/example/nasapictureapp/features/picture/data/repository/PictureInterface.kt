package com.example.nasapictureapp.features.picture.data.repository

import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.networking.RepoResult
import kotlinx.coroutines.flow.Flow

interface PictureInterface {
    suspend fun getImageResponse(): Flow<RepoResult<List<ImageResponse>>>
}