package com.example.ui.home.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.binding.BindingAdapters.loadImage
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.ActivityFixtureDetailsBinding
import com.example.ui.home.FixtureFragment
import com.example.ui.home.adapter.FixtureEventsAdapter
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.extensions.showErrorSnackBar
import com.example.utils.observeEventNotNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityFixtureDetailsBinding

    private val viewModel by viewModels<FixtureDetailsViewModel>()

    lateinit var adapter: FixtureEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fixture_details)

        setInitial()
        initToolbar()
        initAdapter()
        initRecyclerView()
        initViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setInitial() {
        viewModel.setInitialData(intent?.getParcelableExtra(EXTRA_FIXTURE_ITEM))
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewModel() {
        with(viewModel) {

            headerDetailsLiveData.observe(this@FixtureDetailsActivity) {
                binding.teamHomeLogoImageView.loadImage(it.homeTeam.logo)
                binding.teamAwayLogoImageView.loadImage(it.awayTeam.logo)
                binding.scoreTextView.text = getString(R.string.score_label, it.goals.home ?: 0, it.goals.away ?: 0)
                binding.fixtureStatusTextView.text = getString(it.fixture.status.type.message)
            }

            fixtureEventsLiveData.observe(this@FixtureDetailsActivity) {
                adapter.submitList(it)
            }

            loadingLiveData.observe(this@FixtureDetailsActivity) {
                binding.progressBar.isVisible = it
            }

            errorLiveData.observeEventNotNull(this@FixtureDetailsActivity) {
                showErrorSnackBar(binding.root, it)
            }

            emptyEventsLiveData.observe(this@FixtureDetailsActivity) { isEmpty ->
                val title = if (isEmpty) {
                    getString(R.string.empty_fixture_events_message)
                } else {
                    getString(R.string.events)
                }
                binding.titleTextView.text = title
            }
        }
    }

    private fun initAdapter() {
        adapter = FixtureEventsAdapter()
    }

    private fun initRecyclerView() {
        binding.fixtureEventsRecyclerView.apply {
            adapter = this@FixtureDetailsActivity.adapter
            addItemDecoration(DividerItemDecoration(this@FixtureDetailsActivity, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {

        private val EXTRA_FIXTURE_ITEM: String =
            FixtureFragment::class.java.toString() + "_EXTRA_FIXTURE_ITEM"

        fun newIntent(context: Context, leagueFixturesItem: LeagueFixturesItem): Intent {
            return Intent(context, FixtureDetailsActivity::class.java).apply {
                putExtra(EXTRA_FIXTURE_ITEM, leagueFixturesItem)
            }
        }
    }
}