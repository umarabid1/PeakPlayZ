package com.example.peakplays.api

import com.example.peakplays.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface NewsApiService {
    @GET("everything")
    fun getNBANews(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String = "NBA basketball",
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("from") fromDate: String = LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ISO_DATE),
        @Query("to") toDate: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
    ): Call<NewsResponse>

    @GET("everything")
    fun getNFLNews(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String = "NFL football",
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("from") fromDate: String = LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ISO_DATE),
        @Query("to") toDate: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
    ): Call<NewsResponse>
} 