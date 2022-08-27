package com.example.nasapictureapp.networking.response

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class NetworkResponse<T>(
    @SerializedName("status_code")
    val status_code: String,
    @SerializedName("data")
    val data: T,
    @SerializedName("error")
    val error: String? = null,
    var response: ResponseBody
)



