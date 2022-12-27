package com.example.di.net.main

import com.example.di.net.NetworkResult
import com.example.di.net.main.model.FixturesResponse

interface ApiExecutor {

    suspend fun getFixturesByDate(date: String): NetworkResult<FixturesResponse>

    suspend fun getFixturesBetweenTwoDate(season: String, fromDate: String, toDate: String): NetworkResult<FixturesResponse>
}