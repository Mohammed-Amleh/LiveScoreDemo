package com.example.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.FragmentCustomFixtureBinding
import com.example.ui.home.adapter.LeagueFixturesAdapter
import com.example.ui.home.detail.FixtureDetailsActivity
import com.example.utils.asString
import com.example.utils.extensions.launchAndRepeatWithViewLifecycleNotNull
import com.example.utils.extensions.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomFixtureFragment : Fragment() {

    lateinit var binding: FragmentCustomFixtureBinding

    private lateinit var adapter: LeagueFixturesAdapter

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_custom_fixture,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViews()

        launchAndRepeatWithViewLifecycleNotNull(viewModel.customFixturesFlow) {
            adapter.submitList(it)
        }
    }

    private fun initViews() {
        initRecyclerView()

        with(binding) {
            parentRelative.setOnClickListener {
                if (calendarViewLayout.isVisible) {
                    hideCalendarView()
                }
            }

            calendarImageView.setOnClickListener {
                if (calendarViewLayout.isVisible) {
                    hideCalendarView()
                } else {
                    showCalendarView()
                }
            }

            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val date = requireContext().getString(R.string.default_format_date_pattern, year, month, dayOfMonth)

                date.toDate()?.asString()?.let { currentDate ->
                    binding.currentDateTextView.text = currentDate
                    viewModel.getFixturesByDate(currentDate)
                    hideCalendarView()
                }
            }
        }
    }

    private fun hideCalendarView() {
        binding.calendarViewLayout.isVisible = false
        binding.calendarImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.purple_500))
    }

    private fun initRecyclerView() {
        binding.fixturesRecyclerView.apply {
            adapter = this@CustomFixtureFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun initAdapter() {
        adapter = LeagueFixturesAdapter {
            startActivity(FixtureDetailsActivity.newIntent(requireContext(), it))
        }
    }

    private fun showCalendarView() {
        binding.calendarViewLayout.isVisible = true
        binding.calendarImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
    }

    companion object {
        fun newInstance() = CustomFixtureFragment()
    }
}