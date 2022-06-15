package com.example.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.net.NetworkResult
import com.example.di.net.main.ApiExecutor
import com.example.ui.model.FixtureEventItem
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureDetailsViewModel @Inject constructor(
    private val apiExecutor: ApiExecutor
) : ViewModel() {

    private val _headerDetailsLiveData = MutableLiveData<LeagueFixturesItem.Body>()
    val headerDetailsLiveData: LiveData<LeagueFixturesItem.Body> get() = _headerDetailsLiveData

    private val _fixtureEventsLiveData = MutableLiveData<List<FixtureEventItem>>()
    val fixtureEventsLiveData: LiveData<List<FixtureEventItem>> get() = _fixtureEventsLiveData

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Event<Throwable>>()
    val errorLiveData: LiveData<Event<Throwable>> get() = _errorLiveData

    private val _emptyEventsLiveData = MutableLiveData<Boolean>()
    val emptyEventsLiveData: LiveData<Boolean> get() = _emptyEventsLiveData

    fun setInitialData(leagueFixtureItem: LeagueFixturesItem?) {
        if (leagueFixtureItem == null) return

        when (leagueFixtureItem) {
            is LeagueFixturesItem.Body -> {
                _headerDetailsLiveData.value = leagueFixtureItem!!
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

                    _fixtureEventsLiveData.value = fixtureEventsList
                    _emptyEventsLiveData.value = eventList.isEmpty()
                    hideLoading()
                }

                is NetworkResult.ApiError -> {
                    _errorLiveData.value = Event(eventResponse.throwable)
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        _loadingLiveData.value = true
    }

    private fun hideLoading() {
        _loadingLiveData.value = false
    }
}