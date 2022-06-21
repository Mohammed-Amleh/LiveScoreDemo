package com.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.net.NetworkResult
import com.example.di.net.main.ApiExecutor
import com.example.di.net.main.model.fixture.FixtureResponse
import com.example.ui.home.adapter.PageType
import com.example.ui.model.FixtureItem
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.DateUtils
import com.example.utils.asString
import com.example.utils.extensions.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiExecutor: ApiExecutor
) : ViewModel() {

    private val _fixturesFlow = MutableStateFlow<List<FixtureItem>?>(null)
    val fixturesFlow = _fixturesFlow.asStateFlow()

    private val _customFixturesFlow = MutableStateFlow<List<LeagueFixturesItem>?>(null)
    val customFixturesFlow = _customFixturesFlow.asStateFlow()

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow = _loadingFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

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

                    _customFixturesFlow.value = data
                    hideLoading()
                }
                is NetworkResult.ApiError -> {
                    _errorFlow.emit(fixturesResponse.throwable)
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
                        _errorFlow.emit(it.throwable)
                    }
                }
            }
            _fixturesFlow.value = buildFixturesItems(result)
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
        _loadingFlow.value = true
    }

    private fun hideLoading() {
        _loadingFlow.value = false
    }

    private fun executeLunch(callback: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            callback.invoke(this)
        }
    }
}

