package com.example.peakplays.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsItem>
) 