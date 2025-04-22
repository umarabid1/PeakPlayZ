package com.example.peakplays.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.peakplays.R
import com.example.peakplays.models.NewsItem
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val newsList = mutableListOf<NewsItem>()

    fun submitList(news: List<NewsItem>) {
        Log.d("NewsAdapter", "Submitting new list of size: ${news.size}")
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
        Log.d("NewsAdapter", "List updated, size: ${newsList.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        Log.d("NewsAdapter", "Creating new ViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view).also {
            Log.d("NewsAdapter", "ViewHolder created successfully")
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("NewsAdapter", "Binding item at position: $position")
        val newsItem = newsList[position]
        holder.bind(newsItem)
        Log.d("NewsAdapter", "Bound item: ${newsItem.title}")
    }

    override fun getItemCount(): Int {
        return newsList.size.also {
            Log.d("NewsAdapter", "getItemCount called, returning: $it")
        }
    }

    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById<TextView>(R.id.titleTextView).also {
            Log.d("NewsAdapter", "Found titleTextView: $it")
        }
        private val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        private val newsImageView: ImageView = view.findViewById(R.id.newsImageView)
        private val sourceTextView: TextView = view.findViewById(R.id.sourceTextView)
        private val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        fun bind(newsItem: NewsItem) {
            Log.d("NewsAdapter", "Binding news item: ${newsItem.title}")
            titleTextView.text = newsItem.title
            descriptionTextView.text = newsItem.description ?: "No description available"
            sourceTextView.text = "Source: ${newsItem.source.name}"
            
            try {
                val instant = Instant.parse(newsItem.publishedAt)
                val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                dateTextView.text = localDateTime.format(formatter)
            } catch (e: Exception) {
                Log.e("NewsAdapter", "Error parsing date", e)
                dateTextView.text = newsItem.publishedAt
            }

            // Load image using Glide
            newsItem.urlToImage?.let { imageUrl ->
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(newsImageView)
            } ?: run {
                newsImageView.setImageResource(R.drawable.placeholder_image)
            }
            Log.d("NewsAdapter", "Finished binding news item")
        }
    }
    class NewsDiffCallback(
        private val oldList: List<NewsItem>,
        private val newList: List<NewsItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].url == newList[newItemPosition].url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

// Add DiffUtil.Callback for efficient updates
