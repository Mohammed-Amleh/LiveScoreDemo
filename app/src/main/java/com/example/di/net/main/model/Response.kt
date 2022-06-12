package com.example.di.net.main.model


import com.google.gson.annotations.SerializedName

data class FixtureResponse(
    @SerializedName("fixture")
    val fixture: Fixture,

    @SerializedName("goals")
    val goals: Goals,

    @SerializedName("league")
    val league: League,

    @SerializedName("score")
    val score: Score,

    @SerializedName("teams")
    val teams: Teams
)