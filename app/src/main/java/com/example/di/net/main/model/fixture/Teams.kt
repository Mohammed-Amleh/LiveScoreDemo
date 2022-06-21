package com.example.di.net.main.model.fixture


import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("away")
    val away: Away,

    @SerializedName("home")
    val home: Home
)