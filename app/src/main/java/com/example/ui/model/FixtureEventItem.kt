package com.example.ui.model

import com.example.di.net.main.model.event.EventResponse

data class FixtureEventItem(
    val elapsedTime: Int,
    val playerName: String?,
    val teamHomeId: Int,
    val teamAwayId: Int,
    val teamEventId: Int,
    val eventType: String,
) {
    val isHomeTeam get() = teamEventId == teamHomeId

    companion object {
        fun create(eventResponse: EventResponse, teamHomeId: Int, teamAwayId: Int): FixtureEventItem {
            return FixtureEventItem(
                elapsedTime = eventResponse.time.elapsed,
                playerName = eventResponse.player?.name,
                teamHomeId = teamHomeId,
                teamAwayId = teamAwayId,
                teamEventId = eventResponse.team.id,
                eventType = eventResponse.detail
            )
        }
    }
}