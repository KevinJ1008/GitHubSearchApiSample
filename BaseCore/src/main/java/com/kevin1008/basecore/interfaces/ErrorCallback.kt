package com.kevin1008.basecore.interfaces

import com.kevin1008.basecore.utils.ExceptionStatus

interface ErrorCallback {
    fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus? = null)
}