package com.example.ui.model

import com.example.ui.home.adapter.PageType

data class FixtureItem(
    val pageType: PageType,
    val data: List<LeagueFixturesItem>
)

