package com.example.di.net.main.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    @SerializedName("id")
    val id: Int,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("winner")
    val winner: Boolean
) : Parcelable