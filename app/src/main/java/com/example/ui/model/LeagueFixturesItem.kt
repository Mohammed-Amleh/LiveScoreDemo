package com.example.ui.model

import android.os.Parcelable
import com.example.di.net.main.model.Away
import com.example.di.net.main.model.FixtureResponse
import com.example.di.net.main.model.Goals
import com.example.di.net.main.model.Home
import com.example.di.net.main.model.League
import kotlinx.parcelize.Parcelize

sealed class LeagueFixturesItem : Parcelable {
    @Parcelize
    data class Header(
        val leagueId: Int,
        val leagueFlag: String,
        val leagueName: String,
        val leagueLogo: String,
        val leagueSeason: String
    )  :LeagueFixturesItem()

    @Parcelize
    data class Body(
        val awayTeam: Away,
        val homeTeam: Home,
        val goals: Goals
    ) : LeagueFixturesItem()

    companion object {
        fun createHeader(league: League): Header {
            return Header(
                league.id,
                league.flag,
                league.name,
                league.logo,
                league.season.toString(),
            )
        }

        fun createBody(fixtureResponse: FixtureResponse): LeagueFixturesItem {
            return Body(
                fixtureResponse.teams.away,
                fixtureResponse.teams.home,
                fixtureResponse.goals
            )
        }
    }
}