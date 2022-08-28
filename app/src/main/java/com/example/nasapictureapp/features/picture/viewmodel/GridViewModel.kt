package com.example.nasapictureapp.features.picture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nasapictureapp.core.viewmodel.BaseViewModel
import com.example.nasapictureapp.features.picture.data.repository.PictureInterface
import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.networking.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor(private val pictureInterface: PictureInterface) :
    BaseViewModel() {

    private val _imageSources = MutableLiveData<List<ImageResponse>>()
    val imageSources: LiveData<List<ImageResponse>> = _imageSources

    init {
        getPictureResponse()
    }

    private fun getPictureResponse() {
        viewModelScope.launch {
            pictureInterface.getImageResponse().collect { result ->
                when (result) {
                    is RepoResult.Success -> {
                        Timber.e("result $result")
                    }
                    is RepoResult.Loading -> {
                        handleLoading(result.loadingStatus)
                    }
                    else -> {
                        handleFailure(result)
                    }
                }
            }
        }
    }
}