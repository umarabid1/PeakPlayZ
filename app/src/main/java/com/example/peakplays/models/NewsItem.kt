package com.example.peakplays.models

data class NewsItem(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val source: Source,
    var sportType: String? = null // We'll set this manually
) {
    data class Source(
        val id: String?,
        val name: String
    )
} 