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
import com.kevin1008.basecore.utils.InCompleteResultError
import com.kevin1008.basecore.utils.Result

abstract class BaseFragment<VB: ViewBinding> : Fragment(), BaseContract {

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

    override fun handleError(error: Result.Error, callback: ((String?) -> Unit)?) {
        when (error.exception) {
            is InCompleteResultError -> {
                if (error.isFetching) {
                    Snackbar.make(requireView(), R.string.incomplete_result_error, Snackbar.LENGTH_SHORT).show()
                } else {
                    callback?.invoke(activity?.getString(R.string.incomplete_result_error))
                }
            }
            else -> {
                if (error.isFetching) {
                    Snackbar.make(requireView(), error.exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                } else {
                    callback?.invoke(error.exception.message)
                }
            }
        }
    }
}