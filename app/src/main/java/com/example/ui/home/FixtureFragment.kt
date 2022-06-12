package com.example.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.FragmentFixtureBinding
import com.example.ui.home.adapter.LeagueFixturesAdapter
import com.example.ui.model.LeagueFixturesItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureFragment : Fragment() {

    lateinit var binding: FragmentFixtureBinding

    private lateinit var adapter: LeagueFixturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_fixture,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()

        adapter.submitList(getLeagueFixturesList())
    }


    private fun initRecyclerView() {
        binding.leagueFixturesRecyclerView.apply {
            adapter = this@FixtureFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun initAdapter() {
        adapter = LeagueFixturesAdapter()
    }

    private fun getLeagueFixturesList() =
        arguments?.getParcelableArrayList<LeagueFixturesItem>(EXTRA_LEAGUE_FIXTURES_LIST) as List<LeagueFixturesItem>

    companion object {
        private val EXTRA_LEAGUE_FIXTURES_LIST: String =
            FixtureFragment::class.java.toString() + "_EXTRA_LEAGUE_FIXTURES_LIST"

        fun newInstance(data: List<LeagueFixturesItem>): FixtureFragment {
            return FixtureFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_LEAGUE_FIXTURES_LIST, ArrayList(data))
                }
            }
        }
    }
}