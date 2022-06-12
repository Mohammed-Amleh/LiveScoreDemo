package com.example.di.net.main.model


import com.google.gson.annotations.SerializedName

data class Periods(
    @SerializedName("first")
    val first: Int?,

    @SerializedName("second")
    val second: Int?
)