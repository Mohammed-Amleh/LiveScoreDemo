package com.example.di.net.main.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Periods(
    @SerializedName("first")
    val first: Int?,

    @SerializedName("second")
    val second: Int?
):Parcelable