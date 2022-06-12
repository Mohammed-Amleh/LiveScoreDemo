package com.example.di.net.main

import com.example.di.net.main.model.Status
import com.example.di.net.main.model.FixturesResponse
import com.example.di.net.main.model.League
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    @GET("status")
    fun getStatus(): Single<Status>

    // Get all available fixtures from one {date}
    @GET("fixtures")
    fun getFixturesByDate(
        @Query("date") date: String
    ): Single<FixturesResponse>

    // Get all available fixtures from one {date}
    @GET("fixtures")
    fun getFixturesBetweenTwoDate(
        @Query("season") season: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Single<FixturesResponse>
}