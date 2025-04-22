package com.example.peakplays.adapters

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peakplays.R
import com.example.peakplays.models.TeamEvent
import kotlin.apply

class TeamEventAdapter(private val teamEvents: List<TeamEvent>) :
    RecyclerView.Adapter<TeamEventAdapter.TeamEventViewHolder>() {

    // ViewHolder holding a dynamically created view
    inner class TeamEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Assume the first child is eventName and second is eventDate
        val eventNameTextView: TextView = (itemView as LinearLayout).getChildAt(0) as TextView
        val eventDateTextView: TextView = (itemView as LinearLayout).getChildAt(1) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamEventViewHolder {
        // Create a vertical LinearLayout programmatically
        val layout = LinearLayout(parent.context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
        }

        // Create TextView for the event name
        val eventName = TextView(parent.context).apply {
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Create TextView for the event date
        val eventDate = TextView(parent.context).apply {
            textSize = 14f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Add the TextViews to the LinearLayout
        layout.addView(eventName)
        layout.addView(eventDate)

        return TeamEventViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TeamEventViewHolder, position: Int) {
        val event = teamEvents[position]
        holder.eventNameTextView.text = event.name
        holder.eventDateTextView.text = event.date
    }

    override fun getItemCount(): Int = teamEvents.size
}
