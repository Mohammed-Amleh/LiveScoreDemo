package com.example.di.net.main.model.fixture

import com.google.gson.annotations.SerializedName


data class FixturesResponse(
    @SerializedName("response")
    val response: List<FixtureResponse>
)