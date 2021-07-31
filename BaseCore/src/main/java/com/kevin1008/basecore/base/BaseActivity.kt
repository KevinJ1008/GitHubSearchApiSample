package com.kevin1008.basecore.base

import androidx.fragment.app.FragmentActivity
import com.kevin1008.basecore.interfaces.BaseContract
import com.kevin1008.basecore.interfaces.ErrorCallback
import com.kevin1008.basecore.utils.ExceptionStatus
import com.kevin1008.basecore.utils.Result

abstract class BaseActivity : FragmentActivity(), BaseContract, ErrorCallback {

    override fun handleError(error: Result.Error, callback: ErrorCallback?) {
        //Haven't defined, could define base behavior if need
    }

    override fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus?) {
        //interface for descendant to override to handle some customize view behavior
    }
}