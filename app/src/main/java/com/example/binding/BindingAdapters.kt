package com.example.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("goneUnless")
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }


    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    fun imageUri(imageView: ImageView, imageUrl: String?, placeholder: Drawable?) {
        when (imageUrl) {
            null -> {
                Timber.d("Unsetting image url")
                Glide.with(imageView)
                    .load(placeholder)
                    .into(imageView)
            }
            else -> {
                Glide.with(imageView)
                    .load(imageUrl)
                    .apply(RequestOptions().placeholder(placeholder))
                    .into(imageView)
            }
        }
    }
}