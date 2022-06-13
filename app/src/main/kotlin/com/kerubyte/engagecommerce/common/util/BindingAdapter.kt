package com.kerubyte.engagecommerce.common.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {


    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        url?.let { imageUrl ->
            Glide.with(view.context).load(imageUrl).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}