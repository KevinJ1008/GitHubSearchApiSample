package com.kevin1008.basecore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.kevin1008.basecore.BuildConfig
import com.kevin1008.basecore.R
import com.kevin1008.basecore.interfaces.BaseContract
import com.kevin1008.basecore.interfaces.ErrorCallback
import com.kevin1008.basecore.utils.InCompleteResultError
import com.kevin1008.basecore.utils.NoDataException
import com.kevin1008.basecore.utils.Result

abstract class BaseFragment<VB: ViewBinding> : Fragment(), BaseContract, ErrorCallback {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB?
        get() {
            if (BuildConfig.DEBUG) {
                //You should not call your view in background thread or in some anonymous classes if
                // not release when your lifecycle owner is destroy
                return _binding as VB
            }
            return _binding as? VB
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return performOnCreateView(inflater, container, savedInstanceState)
    }

    protected fun performOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * If backend want to control error message, when response is success but have some problem,
     * we could pass response message to custom exception and let this mechanism to get message to show
     */
    override fun handleError(error: Result.Error, callback: ErrorCallback?) {
        when (error.exception) {
            is InCompleteResultError -> {
                if (error.isFetching) {
                    Snackbar.make(requireView(), R.string.incomplete_result_error, Snackbar.LENGTH_SHORT).show()
                } else {
                    callback?.showCustomErrorView(activity?.getString(R.string.incomplete_result_error))
                }
            }
            is NoDataException -> {
                callback?.showCustomErrorView(activity?.getString(R.string.empty_view_no_result), error.exception)
            }
            else -> {
                if (error.isFetching) {
                    Snackbar.make(requireView(), error.exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                } else {
                    callback?.showCustomErrorView(error.exception.message)
                }
            }
        }
    }

    /**
     * For those fragment which want to handle error by itself
     */
    override fun showCustomErrorView(message: String?, exception: Throwable?) {
    }
}