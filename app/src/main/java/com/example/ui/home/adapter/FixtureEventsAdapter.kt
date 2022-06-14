package com.example.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.livescoredemo.R
import com.example.livescoredemo.databinding.ListItemFixtureEventBinding
import com.example.ui.model.FixtureEventItem

class FixtureEventsAdapter :
    ListAdapter<FixtureEventItem, FixtureEventsViewHolder>(object : DiffUtil.ItemCallback<FixtureEventItem>() {
        override fun areItemsTheSame(oldItem: FixtureEventItem, newItem: FixtureEventItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FixtureEventItem, newItem: FixtureEventItem): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureEventsViewHolder {
        val view = ListItemFixtureEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FixtureEventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FixtureEventsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FixtureEventsViewHolder(val binding: ListItemFixtureEventBinding) :
    ViewHolder(binding.root) {

    fun bind(item: FixtureEventItem) {
        binding.elapsedTimeTextView.text =
            itemView.context.getString(R.string.elapsed_time_label, item.elapsedTime)
        if (item.isHomeTeam) {
            binding.playerHomeTeamTextView.text = item.playerName
            binding.playerAwayTeamTextView.isInvisible = true
        } else {
            binding.playerAwayTeamTextView.text = item.playerName
            binding.playerHomeTeamTextView.isInvisible = true
        }
        binding.fixtureEventTypeTextView.text = item.eventType
    }
}