package com.example.ui.home.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.ActivityFixtureDetailsBinding
import com.example.ui.home.FixtureFragment
import com.example.ui.home.adapter.FixtureEventsAdapter
import com.example.ui.model.LeagueFixturesItem
import com.example.utils.extensions.launchAndRepeatWithLifecycle
import com.example.utils.extensions.launchAndRepeatWithLifecycleNotNull
import com.example.utils.extensions.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityFixtureDetailsBinding

    private val viewModel by viewModels<FixtureDetailsViewModel>()

    private lateinit var adapter: FixtureEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fixture_details)

        setInitial()
        initAdapter()
        initViews()
        initViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        initToolbar()
        initRecyclerView()
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
            launchAndRepeatWithLifecycleNotNull(fixtureEventsFlow) {
                adapter.submitList(it)
            }

            launchAndRepeatWithLifecycle(errorFlow) {
                showErrorSnackBar(binding.root, it)
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