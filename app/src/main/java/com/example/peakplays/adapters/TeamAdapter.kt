package com.example.peakplays.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.peakplays.R
import com.example.peakplays.databinding.TeamItemBinding
import com.example.peakplays.models.Team

class TeamAdapter(
    private val teams: List<Team>,
    private val onTeamClick: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(val binding: TeamItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.binding.apply {
            teamName.text = team.name
            
            // Load team logo using Glide with proper error handling
            Glide.with(teamLogo.context)
                .load(team.logoUrl)
                .placeholder(R.drawable.team_logo_placeholder)
                .error(R.drawable.team_logo_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(teamLogo)

            root.setOnClickListener { onTeamClick(team) }
        }
    }

    override fun getItemCount() = teams.size
} 