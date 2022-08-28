package com.example.nasapictureapp.core.repository

import com.example.nasapictureapp.networking.RepoResult
import com.example.nasapictureapp.networking.response.NetworkResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {
    fun <T, P> safeDBCall(
        dbCall: suspend () -> T,
        entityProcessor: (T) -> P
    ): Flow<RepoResult<P>> = flow {
        emit(RepoResult.Loading(true, Source.CACHE))
        val flow = flowOf(safeDBCallInternal(dbCall, entityProcessor))
        emit(RepoResult.Loading(false, Source.CACHE))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    private suspend fun <T, P> safeDBCallInternal(
        dbCall: suspend () -> T,
        entityProcessor: (T) -> P
    ): RepoResult<P> = try {
        RepoResult.Success(entityProcessor.invoke(dbCall.invoke()), Source.CACHE)
    } catch (exception: Exception) {
        RepoResult.Exception(exception, Source.CACHE)
    }

    fun <T, P> safeApiCall(
        apiCall: suspend () -> Response<T>,
        entityProcessor: (T) -> P,
        saveNetworkResult: suspend (T) -> Unit
    ): Flow<RepoResult<P>> = flow {
        emit(RepoResult.Loading(true, Source.REMOTE))
        val flow = flowOf(safeApiCallInternal(apiCall, entityProcessor, saveNetworkResult))
        emit(RepoResult.Loading(false, Source.REMOTE))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T, P> safeApiCallInternal(
        apiCall: suspend () -> Response<T>,
        entityProcessor: (T) -> P,
        saveNetworkResult: suspend (T) -> Unit
    ) = try {
        val apiResult = apiCall.invoke()
        if (apiResult.isSuccessful) {
            val body = apiResult.body()!!
            val json = Gson().toJson(body, NetworkResponse::class.java)
            val responseBody = JSONObject(json)
            when {
                responseBody.has("status_code") && responseBody.get("status_code").toString()
                    .equals("SUCCESS", ignoreCase = true) -> {
                    saveNetworkResult(apiResult.body()!!)
                    RepoResult.Success(entityProcessor(apiResult.body()!!), Source.REMOTE)
                }
                responseBody.has("status_code") && responseBody.get("status_code").toString()
                    .equals("NOT_AVAILABLE", ignoreCase = true) -> {
                    RepoResult.NotAvail(
                        apiResult.code(),
                        responseBody.get("error").toString(),
                        Source.REMOTE
                    )
                }
                responseBody.has("status_code") && responseBody.get("status_code").toString()
                    .equals("FAILURE", ignoreCase = true) -> {
                    RepoResult.Error(
                        apiResult.code(),
                        responseBody.get("error").toString(),
                        Source.REMOTE
                    )
                }
                responseBody.has("status_code") && responseBody.get("status_code").toString()
                    .equals("INVALID", ignoreCase = true) -> {
                    RepoResult.Invalid(
                        apiResult.code(),
                        responseBody.get("error").toString(),
                        Source.REMOTE
                    )
                }
                else -> {
                    //Direct mention of string should be removed from below
                    RepoResult.Error(
                        apiResult.code(),
                        responseBody.get("error").toString(),
                        Source.REMOTE
                    )
                }
            }
        } else {
            RepoResult.Error(apiResult.code(), apiResult.message() ?: "", Source.REMOTE)
        }
    } catch (ex: Exception) {
        RepoResult.Exception(ex, Source.REMOTE)
    }
}
