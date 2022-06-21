package com.example.di.net.main.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Goals(
    val away: Int?,
    val home: Int?
):Parcelable