package com.kevin1008.widget.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.kevin1008.widget.databinding.LayoutEmptyViewBinding

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: LayoutEmptyViewBinding =
        LayoutEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnRetryClickListener(clickListener: OnClickListener) {
        binding.btnError.isVisible = true
        binding.btnError.setOnClickListener(clickListener)
    }

    fun setTitle(title: String?) {
        binding.textErrorTitle.text = title
    }

    fun setButtonVisible(isVisible: Boolean) {
        binding.btnError.isVisible = isVisible
    }
}