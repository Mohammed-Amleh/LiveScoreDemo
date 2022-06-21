package com.example.di.net.main

import com.example.di.net.NetworkResult
import com.example.di.net.main.model.fixture.FixturesResponse
import com.example.di.net.main.model.event.EventsResponse

interface ApiExecutor {

    suspend fun getFixturesByDate(date: String): NetworkResult<FixturesResponse>

    suspend fun getFixtureEvent(fixtureId: Int): NetworkResult<EventsResponse>
}