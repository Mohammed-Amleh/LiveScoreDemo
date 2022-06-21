package com.example.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.executeLunch(callback: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch {
        callback.invoke(this)
    }
}