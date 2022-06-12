package com.example.di.net.main

import com.example.di.net.main.model.FixturesResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiExecutorImpl @Inject constructor(
    private val apis: Apis
) : ApiExecutor {

    override fun getFixturesByDate(date: String): Single<FixturesResponse> {
        return apis.getFixturesByDate(date)
    }

    override fun getFixturesBetweenTwoDate(
        season: String,
        fromDate: String,
        toDate: String
    ): Single<FixturesResponse> {
        return apis.getFixturesBetweenTwoDate(season, fromDate, toDate)
    }
}