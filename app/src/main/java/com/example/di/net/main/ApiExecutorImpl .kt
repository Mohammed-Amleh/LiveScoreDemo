package com.example.di.net.main

import com.example.di.net.NetworkResult
import com.example.di.net.handleApiResponse
import com.example.di.net.main.model.FixturesResponse
import javax.inject.Inject

/**
 *
 * reminder: create CallAdapter & CallAdapterFactory
 * */

class ApiExecutorImpl @Inject constructor(
    private val apis: Apis
) : ApiExecutor {

    override suspend fun getFixturesByDate(date: String): NetworkResult<FixturesResponse> {
        return handleApiResponse { apis.getFixturesByDate(date) }
    }

    override suspend fun getFixturesBetweenTwoDate(
        season: String,
        fromDate: String,
        toDate: String
    ): NetworkResult<FixturesResponse> {
        return handleApiResponse { apis.getFixturesBetweenTwoDate(season, fromDate, toDate) }
    }
}

