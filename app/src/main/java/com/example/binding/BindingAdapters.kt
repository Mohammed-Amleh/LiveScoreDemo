package com.example.binding

import android.view.View
import androidx.core.view.isInvisible
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun visibleInvisible(view: View, show: Boolean) {
        view.isInvisible = !show
    }
}