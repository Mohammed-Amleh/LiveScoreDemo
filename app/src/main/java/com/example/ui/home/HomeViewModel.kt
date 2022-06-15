package com.example.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.net.NetworkResult
import com.example.di.net.main.ApiExecutor
import com.example.di.net.main.model.fixture.FixtureResponse
import com.example.ui.home.adapter.PageType
import com.example.ui.model.FixtureItem
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.DateUtils
import com.example.utils.Event
import com.example.utils.asString
import com.example.utils.extensions.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiExecutor: ApiExecutor
) : ViewModel() {

    private val _fixturesLiveData = MutableLiveData<List<FixtureItem>>()
    val fixturesLiveData: LiveData<List<FixtureItem>> get() = _fixturesLiveData

    private val _customFixturesLiveData = MutableLiveData<List<LeagueFixturesItem>>()
    val customFixturesLiveData: LiveData<List<LeagueFixturesItem>> get() = _customFixturesLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>(false)
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Event<Throwable>>()
    val errorLiveData: LiveData<Event<Throwable>> get() = _errorLiveData

    init {
        getFixtures()
    }

    fun getFixturesByDate(date: String) {
        showLoading()
        executeLunch {
            val fixturesResponse = apiExecutor.getFixturesByDate(date)

            when (fixturesResponse) {
                is NetworkResult.ApiSuccess -> {
                    val fixturesItems = buildFixturesItems(fixturesResponse.data.response)

                    val data = mutableListOf<LeagueFixturesItem>()

                    fixturesItems.forEach { item -> data.addAll(item.data) }

                    _customFixturesLiveData.value = data
                    hideLoading()
                }
                is NetworkResult.ApiError -> {
                    _errorLiveData.value = Event(fixturesResponse.throwable)
                    hideLoading()
                }
            }
        }
    }

    private fun getFixtures() {
        showLoading()
        executeLunch {
            val todayRequest = async { apiExecutor.getFixturesByDate(DateUtils.getTodayDate().asString()) }
            val yesterdayRequest = async { apiExecutor.getFixturesByDate(DateUtils.getYesterdayDate().asString()) }
            val tomorrowRequest = async { apiExecutor.getFixturesByDate(DateUtils.getTomorrowDate().asString()) }


            //if one of request's fail it will not affect other's
            val responsesResult = listOf(todayRequest, yesterdayRequest, tomorrowRequest).map { it.await() }

            val result = mutableListOf<FixtureResponse>()

            responsesResult.forEach {
                when (it) {
                    is NetworkResult.ApiSuccess -> {
                        result.addAll(it.data.response)
                    }
                    is NetworkResult.ApiError -> {
                        _errorLiveData.value = Event(it.throwable)
                    }
                }
            }
            _fixturesLiveData.value = buildFixturesItems(result)
            hideLoading()
        }
    }

    private fun buildFixturesItems(fixturesResponse: List<FixtureResponse>): List<FixtureItem> {

        val fixturesResponseByLeagueDate = fixturesResponse.groupBy {
            Pair(it.league.id, it.fixture.date.toDate()!!.asString())
        }

        return fixturesResponseByLeagueDate.keys.map { (leagueId, date) ->

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

            val fixtureResponseList = fixturesResponseByLeagueDate[Pair(leagueId, date)]!!

            val header =
                LeagueFixturesItem.createHeader(fixtureResponseList.first().league)
            items.add(header)

            val body = fixtureResponseList.map(LeagueFixturesItem::createBody)
            items.addAll(body)

            FixtureItem(pageType, items)
        }
    }

    private fun showLoading() {
        _loadingLiveData.value = true
    }

    private fun hideLoading() {
        _loadingLiveData.value = false
    }

    private fun executeLunch(callback: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            callback.invoke(this)
        }
    }
}

