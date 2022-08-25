package com.example.nasapictureapp.networking

import com.example.nasapictureapp.core.repository.Source


sealed class RepoResult<out T> {
    data class Success<out T>(val data: T, val source: Source) : RepoResult<T>()
    data class Error(val code: Int, val data: String, val source: Source) : RepoResult<Nothing>()
    data class Invalid(val code: Int, val data: String, val source: Source) : RepoResult<Nothing>()
    data class NotAvail(val code: Int, val data: String, val source: Source) : RepoResult<Nothing>()
    data class Exception(val throwable: Throwable, val source: Source) : RepoResult<Nothing>()
    data class Loading(val loadingStatus: Boolean, val source: Source) : RepoResult<Nothing>()
}