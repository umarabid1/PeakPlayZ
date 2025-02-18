package com.example.peakplays.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.peakplays.R
import com.example.peakplays.models.NewsItem
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val newsList = mutableListOf<NewsItem>()

    fun addNBANews(news: List<NewsItem>) {
        news.forEach { it.sportType = "NBA" }
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    fun addNFLNews(news: List<NewsItem>) {
        news.forEach { it.sportType = "NFL" }
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem)
    }

    override fun getItemCount() = newsList.size

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        private val newsImageView: ImageView = view.findViewById(R.id.newsImageView)
        private val sourceTextView: TextView = view.findViewById(R.id.sourceTextView)
        private val sportTypeTextView: TextView = view.findViewById(R.id.sportTypeTextView)
        private val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            descriptionTextView.text = newsItem.description ?: "No description available"
            sourceTextView.text = newsItem.source.name
            sportTypeTextView.text = newsItem.sportType

            // Format and display the date
            val instant = Instant.parse(newsItem.publishedAt)
            val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            dateTextView.text = localDateTime.format(formatter)

            // Load image if available
            newsItem.urlToImage?.let { imageUrl ->
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(newsImageView)
            } ?: run {
                // Set a placeholder image if no image URL is available
                newsImageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
} 