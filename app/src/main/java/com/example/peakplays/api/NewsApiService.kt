package com.example.peakplays.api

import com.example.peakplays.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface NewsApiService {
    @GET("top-headlines")
    fun getSportsNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<NewsResponse>
} 