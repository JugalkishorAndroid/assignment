package com.example.nasapictureapp.features.picture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasapictureapp.features.picture.data.response.ImageResponse

class ImageSharedViewModel: ViewModel() {

    private val _sharedImageItem = MutableLiveData<List<ImageResponse>>()
    val sharedImageItem: LiveData<List<ImageResponse>> = _sharedImageItem

    private var selectedImagePosition = -1

    fun setImageItem(imageResponse: List<ImageResponse>) {
        _sharedImageItem.value = imageResponse
    }

    fun setImagePosition(pos: Int) {
        selectedImagePosition = pos
    }

    fun getSelectedImagePosition(): Int = selectedImagePosition

}