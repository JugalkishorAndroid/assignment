package com.example.nasapictureapp.networking.response

import okhttp3.ResponseBody

data class NetworkResponse<T>(
    var response: ResponseBody
)



