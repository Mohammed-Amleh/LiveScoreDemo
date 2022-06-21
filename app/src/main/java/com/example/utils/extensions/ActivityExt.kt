package com.example.utils.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.livescoredemo.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun AppCompatActivity.showErrorSnackBar(view: View, throwable: Throwable, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, throwable.message ?: "Something going wrong", length)
        .setBackgroundTint(
            ContextCompat.getColor(
                view.context,
                R.color.red
            )
        ).show()
}

fun <T> AppCompatActivity.launchAndRepeatWithLifecycle(
    flow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    lifecycleScope.launch {
        flow.flowWithLifecycle(this@launchAndRepeatWithLifecycle.lifecycle, minActiveState).collect(block)
    }
}

fun <T> AppCompatActivity.launchAndRepeatWithLifecycleNotNull(
    flow: Flow<T?>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    lifecycleScope.launch {
        flow.flowWithLifecycle(this@launchAndRepeatWithLifecycleNotNull.lifecycle, minActiveState).collect {
            it?.let(block)
        }
    }
}