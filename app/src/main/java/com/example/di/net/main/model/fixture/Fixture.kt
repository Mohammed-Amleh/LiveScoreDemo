package com.example.di.net.main.model.fixture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fixture(
    @SerializedName("date")
    val date: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("periods")
    val periods: Periods,

    @SerializedName("referee")
    val referee: String?,

    @SerializedName("status")
    val status: Status,

    @SerializedName("timestamp")
    val timestamp: Int,

    @SerializedName("timezone")
    val timezone: String,
):Parcelable