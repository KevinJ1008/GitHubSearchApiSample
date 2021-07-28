package com.kevin1008.imageloader

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Wrapper, which We could change imageLoader lib easily
 */
fun View?.loadImage(url: String?, @DrawableRes placeHolder: Int) {
    if (this == null) return
    when (this) {
        is ImageView -> {
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(this)
        }

        is ViewGroup -> {
            val viewGroup = this
            Glide.with(viewGroup)
                .load(url)
                .centerCrop()
                .error(placeHolder)
                .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    viewGroup.background = resource
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    viewGroup.background = errorDrawable
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }

        else -> {
            //Not handle TextView, View, or other cases recently
        }
    }
}