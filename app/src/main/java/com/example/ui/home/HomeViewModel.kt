package com.example.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.di.net.main.model.Away
import com.example.di.net.main.model.Extratime
import com.example.di.net.main.model.Fixture
import com.example.di.net.main.model.FixtureResponse
import com.example.di.net.main.model.Fulltime
import com.example.di.net.main.model.Goals
import com.example.di.net.main.model.Halftime
import com.example.di.net.main.model.Home
import com.example.di.net.main.model.League
import com.example.di.net.main.model.Penalty
import com.example.di.net.main.model.Periods
import com.example.di.net.main.model.Score
import com.example.di.net.main.model.Status
import com.example.di.net.main.model.Teams
import com.example.di.net.main.model.Venue
import com.example.ui.home.adapter.PageType
import com.example.ui.model.FixtureItem
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.DateUtils
import com.example.utils.asString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _fixturesLiveData = MutableLiveData<List<FixtureItem>>()
    val fixturesLiveData: LiveData<List<FixtureItem>> = _fixturesLiveData

    init {
        _fixturesLiveData.value = build(mock())
    }

    private fun getFixtures() {
        //TODO
    }


    fun getFixturesByDate(date: String) {
        //TODO
    }


    //Test Data
    private fun mock(): List<FixtureResponse> {
        val league = League("brazil", "", 1, "logog", "Peremier Leuage", "dsa", 2022)
        val league2 = League("USA", "", 2, "logog", "USA Leuage", "dsa", 2022)
        val league3 = League("China", "", 3, "logog", "China Leuage", "dsad", 2022)

        val fixture = Fixture(
            DateUtils.getYesterdayDate().asString(),
            1,
            Periods(1, 2),
            "dsa",
            Status(1, "das", "jh"),
            321,
            "sasa",
            Venue("dasd", 5, "name")
        )
        val fixture2 = Fixture(
            DateUtils.getTodayDate().asString(),
            1,
            Periods(1, 2),
            "dsa",
            Status(1, "das", "jh"),
            123321,
            "sasa",
            Venue("dasd", 5, "name")
        )
        val fixture3 = Fixture(
            DateUtils.getTomorrowDate().asString(),
            1,
            Periods(1, 2),
            "dsa",
            Status(1, "das", "jh"),
            112323,
            "sasa",
            Venue("dasd", 5, "name")
        )

        val goals = Goals(1, 2)
        val score = Score(Extratime(1, 2), Fulltime(10, 20), Halftime(25, 34), Penalty(1, null))
        val Teams = Teams(
            Away(1, "lgog", "USA", true), Home(2, "logo", "chinda", false)
        )
        val Teams2 = Teams(
            Away(2, "lgog", "Palestine", true), Home(2, "logo", "Canada", false)
        )
        val fixtureResponse2 = FixtureResponse(fixture, Goals(1, 5), league, score, Teams)
        val fixtureResponse3 = FixtureResponse(fixture, Goals(4, 5), league2, score, Teams)
        val fixRes = FixtureResponse(fixture, goals, league, score, Teams2)
        val dsd2 = FixtureResponse(fixture2, goals, league2, score, Teams)
        val dsa = FixtureResponse(fixture3, goals, league, score, Teams2)

        return listOf(fixtureResponse2, fixtureResponse3, fixRes, dsa, dsd2)
    }

    fun build(fixturesResponse: List<FixtureResponse>): List<FixtureItem> {

        val fixturesResponseByLeagueDate = fixturesResponse.groupBy { Pair(it.league, it.fixture.date) }

        return fixturesResponseByLeagueDate.keys.map { (league, date) ->

            val pageType = when (date) {
                DateUtils.getYesterdayDate().asString() -> {
                    PageType.YESTERDAY
                }
                DateUtils.getTodayDate().asString() -> {
                    PageType.TODAY
                }
                DateUtils.getTomorrowDate().asString() -> {
                    PageType.TOMORROW
                }
                else -> {
                    PageType.CUSTOM
                }
            }

            val items = mutableListOf<LeagueFixturesItem>()

            val header = LeagueFixturesItem.createHeader(league)
            items.add(header)

            val body = fixturesResponseByLeagueDate[Pair(league, date)]!!.map(LeagueFixturesItem::createBody)
            items.addAll(body)

            FixtureItem(pageType, items)
        }
    }
}