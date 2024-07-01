package com.example.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.ActivityHomeBinding
import com.example.ui.home.adapter.PageType
import com.example.ui.home.adapter.FixturesViewPagerAdapter
import com.example.ui.model.FixtureItem
import com.example.utils.extensions.showErrorSnackBar
import com.example.utils.observeEventNotNull
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    private lateinit var fixturesViewPagerAdapter: FixturesViewPagerAdapter

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.tag == PageType.CUSTOM.position) {
                    hideTabLayout()
                } else {
                    showTabLayout()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                ///ntd,
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //ntd
            }
        })
    }

    private fun initViewModel() {
        viewModel.fixturesLiveData.observe(this) {
            initViewPager(it)

            /**
             * [TabLayoutMediator.attach()] must called after view pager has an adapter
             * */
            initTabLayoutMediator().attach()
        }

        viewModel.loadingLiveData.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.errorLiveData.observeEventNotNull(this) {
            showErrorSnackBar(binding.root, it)
        }
    }

    private fun initViewPager(fixtureItemsList: List<FixtureItem>) {
        fixturesViewPagerAdapter = FixturesViewPagerAdapter(this, fixtureItemsList)
        with(binding.viewpager) {
            adapter = fixturesViewPagerAdapter
            currentItem = PageType.TODAY.position
            offscreenPageLimit = 5
        }
    }

    private fun initTabLayoutMediator(): TabLayoutMediator {
        return TabLayoutMediator(
            binding.tabLayout,
            binding.viewpager,
            false,
            true
        ) { tab, position ->
            val pageType = PageType.getPageType(position)

            val title = pageType?.title?.let { stringRes -> getString(stringRes) }

            if (!title.isNullOrEmpty()) {
                tab.text = title
            } else {
                //default icon for untitled paged
                tab.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_calendar_month_24
                )
            }
            tab.tag = position
        }
    }

    private fun hideTabLayout() {
        binding.tabLayout.isVisible = false
    }

    private fun showTabLayout() {
        binding.tabLayout.isVisible = true
    }
}
