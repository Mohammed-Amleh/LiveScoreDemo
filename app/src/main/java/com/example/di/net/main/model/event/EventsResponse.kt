package com.example.di.net.main.model.event


import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("response")
    val response: List<EventResponse>
)