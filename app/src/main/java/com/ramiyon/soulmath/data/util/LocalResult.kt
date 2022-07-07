package com.ramiyon.soulmath.data.util

sealed class LocalResult<out T> {
    data class Success<out T> (val data: T): LocalResult<T>()
    data class Error(val errorMessage: String): LocalResult<Nothing>()
    data class Empty(val message: String = ""): LocalResult<Nothing>()
}