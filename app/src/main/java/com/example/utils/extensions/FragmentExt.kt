package com.example.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.launchAndRepeatWithViewLifecycle(
    flow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(this@launchAndRepeatWithViewLifecycle.viewLifecycleOwner.lifecycle, minActiveState).collect(block)
    }
}

fun <T> Fragment.launchAndRepeatWithViewLifecycleNotNull(
    flow: Flow<T?>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(this@launchAndRepeatWithViewLifecycleNotNull.viewLifecycleOwner.lifecycle, minActiveState).collect {
            it?.let(block)
        }
    }
}