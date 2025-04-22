package com.example.peakplays.api

import com.example.peakplays.api.SportsApi.TeamLiveRes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsLiveApi {


    @GET("v2_livescore_example.json")
    suspend fun teamLive(): TeamLiveResponse

    companion object {
        const val BASE_URL = "https://www.thesportsdb.com/xml/"
    }

    data class TeamLiveResponse(
        val livescore: List<LiveData> = emptyList()
    )

    data class LiveData(
        val strHomeTeam: String?,
        val strAwayTeam: String?,
        val intHomeScore: String?,
        val intAwayScore: String?
    )

}







