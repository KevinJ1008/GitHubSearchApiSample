package com.kevin1008.basecore.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.kevin1008.basecore.BuildConfig

/**
 * Activity may not need a layout in some cases, so just create a skeleton for descendants who want
 *
 * Usage:
 * class TestActivity: BaseViewBindingActivity<{$YourViewBindingClassName}>() {
 *      override val bindingInflater: (LayoutInflater) -> {$YourViewBindingClassName} = {$YourViewBindingClassName}::inflate
 *      //If you want write code clearly, you could write as:
 *      override val bindingInflater: (LayoutInflater) -> {$YourViewBindingClassName} = { inflater
 *          {$YourViewBindingClassName}.inflate(inflater)
 *      }
 *      override fun onCreate(savedInstanceState: Bundle?) {
 *          super.onCreate(savedInstanceState)
 *          //use binding class to reference view to do what you want after super
 *          binding.{$view_id}.{$view_method_you_want_to_use}
 *      }
 * }
 */
abstract class BaseViewBindingActivity<VB: ViewBinding> : BaseActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (inflater: LayoutInflater) -> VB

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding!!.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}