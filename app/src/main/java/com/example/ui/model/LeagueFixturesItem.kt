package com.example.ui.model

import android.os.Parcelable
import com.example.di.net.main.model.fixture.Away
import com.example.di.net.main.model.fixture.Fixture
import com.example.di.net.main.model.fixture.FixtureResponse
import com.example.di.net.main.model.fixture.Goals
import com.example.di.net.main.model.fixture.Home
import com.example.di.net.main.model.fixture.League
import com.example.di.net.main.model.fixture.Status
import com.example.di.net.main.model.fixture.StatusType
import kotlinx.parcelize.Parcelize

sealed class LeagueFixturesItem : Parcelable {
    @Parcelize
    data class Header(
        val leagueId: Int,
        val leagueFlag: String?,
        val leagueName: String,
        val leagueLogo: String,
        val leagueSeason: String
    ) : LeagueFixturesItem()

    @Parcelize
    data class Body(
        val fixture: Fixture,
        val awayTeam: Away,
        val homeTeam: Home,
        val goals: Goals,
        val status: Status

    ) : LeagueFixturesItem() {
        val isMatchLive
            get() = when (status.type) {
                StatusType.H1, StatusType.H2, StatusType.HT, StatusType.ET, StatusType.P, StatusType.BT -> {
                    true
                }
                else -> {
                    false
                }
            }
    }

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
                fixtureResponse.fixture,
                fixtureResponse.teams.away,
                fixtureResponse.teams.home,
                fixtureResponse.goals,
                fixtureResponse.fixture.status
            )
        }
    }
}