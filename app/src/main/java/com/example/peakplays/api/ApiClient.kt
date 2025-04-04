package com.example.peakplays.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(SportsApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val sportsApi: SportsApi = retrofit.create(SportsApi::class.java)
}