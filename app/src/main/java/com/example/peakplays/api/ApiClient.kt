package com.example.peakplays.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(SportsApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val sportsApi: SportsApi = retrofit.create(SportsApi::class.java)

    private val retrofit2 = Retrofit.Builder()
        .baseUrl(SportsLiveApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val sportsLiveApi: SportsLiveApi = retrofit2.create(SportsLiveApi::class.java)

    private val retrofit3 = Retrofit.Builder()
        .baseUrl(TheSportsDBApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val theSportsDBApi: TheSportsDBApi = retrofit3.create(TheSportsDBApi::class.java)
    // ⬆️ Correct! Use retrofit3 not retrofit2
}
