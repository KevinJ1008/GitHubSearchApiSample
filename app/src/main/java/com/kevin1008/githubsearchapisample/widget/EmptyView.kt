package com.kevin1008.githubsearchapisample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kevin1008.githubsearchapisample.databinding.LayoutEmptyViewBinding

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: LayoutEmptyViewBinding =
        LayoutEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnRetryClickListener(clickListener: OnClickListener) {
        binding.btnError.setOnClickListener(clickListener)
    }

    fun setTitle(title: String?) {
        binding.textErrorTitle.text = title
    }

}