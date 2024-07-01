package com.example.di.net.main

import com.example.di.net.main.model.fixture.FixturesResponse
import com.example.di.net.main.model.event.EventsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    // Get all available fixtures from one {date}
    @GET("fixtures")
    suspend fun getFixturesByDate(
        @Query("date") date: String
    ): Response<FixturesResponse>

    @GET("fixtures/events")
    suspend fun getFixtureEvents(
        @Query("fixture") fixtureId: String
    ):Response<EventsResponse>
}