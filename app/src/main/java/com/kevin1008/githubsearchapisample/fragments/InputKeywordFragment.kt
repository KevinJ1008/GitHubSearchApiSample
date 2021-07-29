package com.kevin1008.githubsearchapisample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevin1008.basecore.base.BaseFragment
import com.kevin1008.basecore.utils.hideKeyboard
import com.kevin1008.githubsearchapisample.R
import com.kevin1008.githubsearchapisample.databinding.FragmentInputBinding

class InputKeywordFragment : BaseFragment<FragmentInputBinding>() {

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentInputBinding = FragmentInputBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnInput?.setOnClickListener {
            val keyword = binding?.editInputName?.text?.toString() ?: ""
            if (keyword.trim().isEmpty()) {
                binding?.layoutKeywordContainer?.apply {
                    Snackbar.make(this, R.string.input_toast_name, Snackbar.LENGTH_SHORT).show()
                }
                return@setOnClickListener
            }
            hideKeyboard()
            val action = InputKeywordFragmentDirections.actionInputKeywordToSearchResult(keyword = keyword)
            findNavController().navigate(action)
        }
        binding?.layoutKeywordContainer?.setOnClickListener {
            hideKeyboard()
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.btnInput?.isClickable = true
    }

    override fun onPause() {
        super.onPause()
        binding?.btnInput?.isClickable = false
    }
}