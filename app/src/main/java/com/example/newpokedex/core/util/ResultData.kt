package com.example.newpokedex.core.util

sealed class ResultData<out T> {
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Failure(val exception: Throwable) : ResultData<Nothing>()
    data object Loading : ResultData<Nothing>()
}