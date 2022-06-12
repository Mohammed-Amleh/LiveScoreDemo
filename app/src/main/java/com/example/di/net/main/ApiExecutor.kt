package com.example.di.net.main

import com.example.di.net.main.model.FixturesResponse
import io.reactivex.Single

interface ApiExecutor {

    fun getFixturesByDate(date: String): Single<FixturesResponse>

    fun getFixturesBetweenTwoDate(season:String,fromDate: String, toDate: String): Single<FixturesResponse>
}