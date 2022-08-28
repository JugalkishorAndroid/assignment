package com.example.nasapictureapp.features.picture.data.repository

import com.example.nasapictureapp.core.repository.BaseRepository
import com.example.nasapictureapp.core.repository.Source
import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.networking.RepoResult
import com.example.nasapictureapp.util.InternetConnectivity
import com.example.nasapictureapp.util.MockResponseFileReader
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val internetConnectivity: InternetConnectivity
) : PictureInterface, BaseRepository() {

    override suspend fun getImageResponse(): Flow<RepoResult<List<ImageResponse>>> {
        /*Check internet connectivity inside repo if needed*/
        return if (internetConnectivity.isNetworkAvailable()) {
            /*Make real time network by using SafeApiCall Both network and offline data handled here. If need we can used
            safeApiCall({
            }, {
               data -> {}
            }, { response -> Timber.e("$response") })*/
            val purchase: List<ImageResponse> = Gson().fromJson(
                MockResponseFileReader("demo.json").content,
                Array<ImageResponse>::class.java
            ).toList()
            return flowOf(RepoResult.Success(purchase, Source.REMOTE))

        } else {
            flow {
                flowOf(RepoResult.Error(800, "No Internet Connection", Source.REMOTE))
            }
        }
    }


}