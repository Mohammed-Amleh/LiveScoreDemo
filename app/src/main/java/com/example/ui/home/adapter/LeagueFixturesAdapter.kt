package com.example.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.ListItemFixtureHeaderBinding
import com.example.livescoredemo.databinding.ListItemFixturesBinding
import com.example.ui.model.LeagueFixturesItem

class LeagueFixturesAdapter(
    private val onClick: (LeagueFixturesItem) -> Unit
) :
    ListAdapter<LeagueFixturesItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<LeagueFixturesItem>() {
        override fun areItemsTheSame(oldItem: LeagueFixturesItem, newItem: LeagueFixturesItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LeagueFixturesItem, newItem: LeagueFixturesItem): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LEAGUE_HEADER_VIEW_TYPE) {
            LeagueFixturesHeaderViewHolder(
                ListItemFixtureHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LeagueFixturesBodyViewHolder(
                ListItemFixturesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ) { index ->
                if (index != RecyclerView.NO_POSITION) {
                    onClick(getItem(index))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is LeagueFixturesHeaderViewHolder -> {
                holder.bind(item as LeagueFixturesItem.Header)
            }
            is LeagueFixturesBodyViewHolder -> {
                holder.bind(item as LeagueFixturesItem.Body)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is LeagueFixturesItem.Header -> LEAGUE_HEADER_VIEW_TYPE
            is LeagueFixturesItem.Body -> TEAM_BODY_VIEW_TYPE
        }
    }

    companion object {
        const val LEAGUE_HEADER_VIEW_TYPE = 1
        const val TEAM_BODY_VIEW_TYPE = 2
    }
}

internal class LeagueFixturesHeaderViewHolder(private val binding: ListItemFixtureHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LeagueFixturesItem.Header) {
        binding.header = item
    }
}

internal class LeagueFixturesBodyViewHolder(private val binding: ListItemFixturesBinding, onClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun bind(item: LeagueFixturesItem.Body) {
        binding.team = item
        binding.fixtureStatusTextView.text = itemView.context.getString(item.status.type.message)
        if (item.isMatchLive) {
            val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_fire)
            binding.fixtureStatusTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, drawable, null)
        } else {
            binding.fixtureStatusTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }
}