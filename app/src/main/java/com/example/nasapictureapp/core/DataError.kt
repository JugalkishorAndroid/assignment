package com.example.nasapictureapp.core

sealed class DataError {
    class Code(val errorCode: Int): DataError()
    class Message(val errorCode: Int, val errorMessage: String): DataError()
}
