package com.example.di.net.main.model.event


import com.google.gson.annotations.SerializedName

data class Assist(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
)