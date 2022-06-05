package com.example.di.net.main.model


import com.google.gson.annotations.SerializedName

data class Requests(
    val current: Int,
    @SerializedName("limit_day")
    val limitDay: Int
)