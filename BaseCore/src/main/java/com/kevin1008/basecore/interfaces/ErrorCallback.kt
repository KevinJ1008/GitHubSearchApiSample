package com.kevin1008.basecore.interfaces

interface ErrorCallback {
    fun showCustomErrorView(message: String?, exception: Throwable? = null)
}