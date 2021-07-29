package com.kevin1008.basecore.utils

sealed class Result<out T: Any> {
    class Success<out T: Any>(val data: T) : Result<T>()
    class Error(val exception: Throwable, val isFetching: Boolean = false) : Result<Nothing>()
}

class InCompleteResultError : Error()