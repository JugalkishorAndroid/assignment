package com.example.nasapictureapp.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasapictureapp.core.DataError
import com.example.nasapictureapp.core.repository.Source
import com.example.nasapictureapp.networking.RepoResult
import com.google.gson.stream.MalformedJsonException
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.SocketException

abstract class BaseViewModel : ViewModel() {

    private val TAG = "BaseViewModel"

    private val _errorMessage = MutableLiveData<DataError>()
    val errorMessage: LiveData<DataError> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    protected fun handleLoading(loadingStatus: Boolean) {
        _loading.value = loadingStatus
    }

    fun setLoading(loading: Boolean) {
        handleLoading(loading)
    }

    protected fun handleError(
        errorCode: Int,
        errorMessage: String,
        apiServiceKey: String,
        request: Any? = null
    ) {
        if (errorMessage.isNotBlank()) {
            _errorMessage.postValue(DataError.Message(errorCode, errorMessage))
        } else {
            _errorMessage.postValue(DataError.Code(errorCode))
        }
    }

    protected fun handleException(throwable: Throwable) {
        _loading.value = false
        when (throwable) {
            is InterruptedIOException -> {
                _errorMessage.postValue(DataError.Code(800))
            }
            is SocketException -> {
                _errorMessage.postValue(DataError.Code(801))
            }

            is MalformedJsonException -> {
                _errorMessage.postValue(DataError.Code(999))
            }
            else -> {
                _errorMessage.postValue(DataError.Message(999, throwable.localizedMessage))
            }
        }
    }

    protected fun <T> handleFailure(
        repoResult: RepoResult<T>,
        apiServiceKey: String = "",
        request: Any? = null
    ) {
        _loading.value = false
        when (repoResult) {
            is RepoResult.Error -> {
                when (repoResult.source) {
                    Source.CACHE -> {
                        Timber.d("handleFailure: Error.Source.CACHE -> ${repoResult.data}")
                    }
                    Source.REMOTE -> {
                        Timber.d("handleFailure: Error.Source.REMOTE -> ${repoResult.data}")
                    }

                }
                handleError(repoResult.code, repoResult.data, apiServiceKey, request)
            }
            is RepoResult.Exception -> {
                when (repoResult.source) {
                    Source.CACHE -> {
                        Timber.d(
                            "handleFailure: Exception.Source.CACHE -> ${repoResult.throwable}"
                        )
                    }
                    Source.REMOTE -> {
                        Timber.d(
                            "handleFailure: Exception.Source.REMOTE -> ${repoResult.throwable}"
                        )
                    }

                }
                handleException(repoResult.throwable)
            }
            else -> {}
        }
    }
}
