package com.example.peakplays.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peakplays.R
import com.example.peakplays.models.Player
import android.util.Log

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    private var players: List<Player> = emptyList()

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.playerName)
        val positionTextView: TextView = itemView.findViewById(R.id.playerPosition)
        val numberTextView: TextView = itemView.findViewById(R.id.playerNumber)
        val detailsTextView: TextView = itemView.findViewById(R.id.playerDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        Log.d("PlayerAdapter", "Binding player: ${player.name} at position $position")
        
        holder.nameTextView.text = holder.itemView.context.getString(R.string.player_name, player.name)
        holder.positionTextView.text = holder.itemView.context.getString(R.string.player_position, player.position)
        holder.numberTextView.text = holder.itemView.context.getString(R.string.player_number, player.number)
        
        val details = listOfNotNull(
            player.height.takeIf { it != "N/A" }?.let { holder.itemView.context.getString(R.string.player_height, it) },
            player.weight.takeIf { it != "N/A" }?.let { holder.itemView.context.getString(R.string.player_weight, it) },
            player.nationality?.let { holder.itemView.context.getString(R.string.player_nationality, it) },
            player.dateOfBirth?.let { holder.itemView.context.getString(R.string.player_dob, it) }
        ).joinToString(" • ")
        
        holder.detailsTextView.text = details
    }

    override fun getItemCount(): Int = players.size

    fun updatePlayers(newPlayers: List<Player>) {
        Log.d("PlayerAdapter", "Updating players list with ${newPlayers.size} players")
        players = newPlayers
        notifyDataSetChanged()
    }
} 