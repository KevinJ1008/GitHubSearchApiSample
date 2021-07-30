package com.kevin1008.githubsearchapisample.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevin1008.basecore.base.BaseFragment
import com.kevin1008.basecore.utils.hideKeyboard
import com.kevin1008.githubsearchapisample.R
import com.kevin1008.githubsearchapisample.databinding.FragmentInputBinding

class InputKeywordFragment : BaseFragment<FragmentInputBinding>() {

    override val bindingInflater: (
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) -> FragmentInputBinding = FragmentInputBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewSetting()
    }

    override fun onResume() {
        super.onResume()
        binding?.btnInput?.isClickable = true
    }

    override fun onPause() {
        super.onPause()
        binding?.btnInput?.isClickable = false
    }

    private fun initViewSetting() {
        binding?.btnInput?.setOnClickListener {
            val keyword = binding?.editInputName?.text?.toString() ?: ""
            if (shouldShowEmptyHint(keyword = keyword)) {
                return@setOnClickListener
            }
            goSearchResult(keyword = keyword)
        }
        binding?.layoutKeywordContainer?.setOnClickListener {
            hideKeyboard()
        }
        binding?.editInputName?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (KeyEvent.ACTION_DOWN == event?.action
                        && KeyEvent.KEYCODE_ENTER == event.keyCode
                        && 0 == event.repeatCount)
            ) {
                (view as? EditText)?.apply {
                    if (shouldShowEmptyHint(this.text.toString())) {
                        return@setOnEditorActionListener false
                    }
                    goSearchResult(keyword = this.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun shouldShowEmptyHint(keyword: String): Boolean {
        return if (keyword.trim().isEmpty()) {
            Snackbar.make(requireView(), R.string.input_toast_name, Snackbar.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    private fun goSearchResult(keyword: String) {
        hideKeyboard()
        val action = InputKeywordFragmentDirections.actionInputKeywordToSearchResult(keyword = keyword)
        findNavController().navigate(action)
    }
}