package com.example.di.net.main.model.event


import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("elapsed")
    val elapsed: Int,

    @SerializedName("extra")
    val extra: Int?
)