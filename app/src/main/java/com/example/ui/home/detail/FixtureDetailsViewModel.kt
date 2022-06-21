package com.example.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.net.NetworkResult
import com.example.di.net.main.ApiExecutor
import com.example.ui.model.FixtureEventItem
import com.example.ui.model.LeagueFixturesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureDetailsViewModel @Inject constructor(
    private val apiExecutor: ApiExecutor
) : ViewModel() {


    private val _headerDetailsFlow = MutableStateFlow<LeagueFixturesItem.Body?>(null)
    val headerDetailsFlow = _headerDetailsFlow.asStateFlow()

    private val _fixtureEventsFlow = MutableStateFlow<List<FixtureEventItem>?>(null)
    val fixtureEventsFlow = _fixtureEventsFlow.asStateFlow()

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow = _loadingFlow.asStateFlow()

    private val _emptyEventsFlow = MutableStateFlow(true)
    val emptyEventsFlow = _emptyEventsFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun setInitialData(leagueFixtureItem: LeagueFixturesItem?) {
        if (leagueFixtureItem == null) return

        when (leagueFixtureItem) {
            is LeagueFixturesItem.Body -> {
                _headerDetailsFlow.value = leagueFixtureItem!!
                getFixtureEvents(leagueFixtureItem)
            }
            is LeagueFixturesItem.Header -> {
                /*** for future work* */
            }
        }
    }

    private fun getFixtureEvents(fixtureItem: LeagueFixturesItem.Body) {
        showLoading()
        viewModelScope.launch {
            when (val eventResponse = apiExecutor.getFixtureEvent(fixtureItem.fixture.id)) {
                is NetworkResult.ApiSuccess -> {

                    val eventList = eventResponse.data.response

                    val fixtureEventsList = eventList.map {
                        FixtureEventItem.create(it, fixtureItem.homeTeam.id, fixtureItem.awayTeam.id)
                    }

                    _fixtureEventsFlow.value = fixtureEventsList
                    _emptyEventsFlow.value = eventList.isEmpty()
                    hideLoading()
                }

                is NetworkResult.ApiError -> {
                    _errorFlow.emit(eventResponse.throwable)
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        _loadingFlow.value = true
    }

    private fun hideLoading() {
        _loadingFlow.value = false
    }
}