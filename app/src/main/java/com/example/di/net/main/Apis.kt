package com.example.di.net.main

import com.example.di.net.main.model.FixturesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    // Get all available fixtures from one {date}
    @GET("fixtures")
    suspend fun getFixturesByDate(
        @Query("date") date: String
    ): Response<FixturesResponse>

    // Get all available fixtures from between 2 dates
    @GET("fixtures")
    suspend fun getFixturesBetweenTwoDate(
        @Query("season") season: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Response<FixturesResponse>
}