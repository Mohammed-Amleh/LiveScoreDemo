package com.example.ui.home.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.livescoredemo.R
import com.example.ui.home.CustomFixtureFragment
import com.example.ui.home.FixtureFragment
import com.example.ui.model.FixtureItem
import com.example.ui.model.LeagueFixturesItem

class FixturesViewPagerAdapter(
    activity: FragmentActivity,
    private val fixtureItemsList: List<FixtureItem>,
) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val pageType = PageType.getPageType(position)

        val data = mutableListOf<LeagueFixturesItem>()

        fixtureItemsList.forEach {
            if (it.pageType == pageType) {
                data.addAll(it.data)
            }
        }

        return when (pageType) {
            PageType.LIVE -> FixtureFragment.newInstance(data)
            PageType.YESTERDAY -> FixtureFragment.newInstance(data)
            PageType.TODAY -> FixtureFragment.newInstance(data)
            PageType.TOMORROW -> FixtureFragment.newInstance(data)
            PageType.CUSTOM -> CustomFixtureFragment.newInstance()
            else -> {
                throw Throwable("unsupported page")
            }
        }
    }


    override fun getItemCount() = 5
}


enum class PageType(val position: Int, @StringRes val title: Int?) {
    LIVE(0, R.string.live),
    YESTERDAY(1, R.string.yesterday),
    TODAY(2, R.string.today),
    TOMORROW(3, R.string.tomorrow),
    CUSTOM(4, null);

    companion object {
        fun getPageType(position: Int): PageType? {
            return values().find {
                it.position == position
            }
        }
    }
}