package com.kevin1008.basecore.interfaces

import com.kevin1008.basecore.utils.Result

interface BaseContract {
    /**
     * @param error to determine handle what error
     * @param callback to let subclass to show what they want
     */
    fun handleError(error: Result.Error, callback: ErrorCallback? = null)
}