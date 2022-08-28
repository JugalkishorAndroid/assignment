package com.example.nasapictureapp.features.picture.data.repository

import com.example.nasapictureapp.core.repository.BaseRepository
import com.example.nasapictureapp.core.repository.Source
import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.networking.RepoResult
import com.example.nasapictureapp.util.MockResponseFileReader
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PictureFakeRepository @Inject constructor(
) : PictureInterface, BaseRepository() {

    override suspend fun getImageResponse(): Flow<RepoResult<List<ImageResponse>>> {
        val purchase: List<ImageResponse> = Gson().fromJson(
            MockResponseFileReader("demo.json").content,
            Array<ImageResponse>::class.java
        ).toList()
        return flowOf(RepoResult.Success(purchase, Source.REMOTE))
    }
}