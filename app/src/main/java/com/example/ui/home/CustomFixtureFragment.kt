package com.example.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.FragmentCustomFixtureBinding
import com.example.utils.asString
import com.example.utils.extensions.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomFixtureFragment : Fragment() {

    lateinit var binding: FragmentCustomFixtureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_custom_fixture,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
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
                val date = ("${year}-${month}-${dayOfMonth}")
                    .toDate()?.asString()

                date?.let { currentDate ->
                    binding.currentDateTextView.text = currentDate
                }
            }
        }
    }

    private fun hideCalendarView() {
        binding.calendarViewLayout.isVisible = false
        binding.calendarImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.purple_500))
    }

    private fun showCalendarView() {
        binding.calendarViewLayout.isVisible = true
        binding.calendarImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
    }

    companion object {
        fun newInstance() = CustomFixtureFragment()
    }
}