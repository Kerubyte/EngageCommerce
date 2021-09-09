package com.kerubyte.engagecommerce.infrastructure.util

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
}