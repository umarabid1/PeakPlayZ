package com.example.peakplays.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peakplays.models.LiveEvent
import kotlin.apply

class LiveEventAdapter(private val liveEvents: List<LiveEvent>) :
    RecyclerView.Adapter<LiveEventAdapter.LiveEventViewHolder>() {

    inner class LiveEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layout = itemView as LinearLayout
        val teamNamesTextView: TextView = layout.getChildAt(0) as TextView
        val scoresTextView: TextView = layout.getChildAt(1) as TextView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveEventViewHolder {
        val layout = LinearLayout(parent.context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
        }

        val teamNamesTextView = TextView(parent.context).apply {
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val scoresTextView = TextView(parent.context).apply {
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        layout.addView(teamNamesTextView)
        layout.addView(scoresTextView)

        return LiveEventViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LiveEventViewHolder, position: Int) {
        val event = liveEvents[position]
        holder.teamNamesTextView.text = "${event.homeTeam} vs ${event.awayTeam}"
        holder.scoresTextView.text = "${event.homeScore} - ${event.awayScore}"
    }

    override fun getItemCount(): Int = liveEvents.size
}
