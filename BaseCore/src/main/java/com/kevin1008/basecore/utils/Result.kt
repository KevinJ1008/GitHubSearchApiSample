package com.kevin1008.basecore.utils

sealed class Result<out T: Any> {
    class Success<out T: Any>(val data: T) : Result<T>()
    class Error(val exception: Throwable, val isFetching: Boolean = false) : Result<Nothing>()
}

class APIException(val exceptionStatus: ExceptionStatus, val errorCode: Int? = null) : Exception() {
    override fun toString(): String {
        return exceptionStatus.message.toString()
    }
}

sealed class ExceptionStatus(val message: String?) {
    object INCOMPLETE_RESULT_ERROR : ExceptionStatus("Get incomplete result, timeout from server may happened")
    object NO_DATA_ERROR : ExceptionStatus("No data error")
    object UNKNOWN_ERROR : ExceptionStatus("Unknown error occurred, please try again later")
    class CUSTOM_ERROR(message: String?) : ExceptionStatus(message)
}