package com.example.utils.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.livescoredemo.R
import com.google.android.material.snackbar.Snackbar


fun AppCompatActivity.showErrorSnackBar(view: View, throwable: Throwable, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, throwable.message ?: "Something going wrong", length)
        .setBackgroundTint(
            ContextCompat.getColor(
                view.context,
                R.color.red
            )
        ).show()
}