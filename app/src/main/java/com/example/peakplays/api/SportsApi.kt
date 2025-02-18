package com.example.peakplays.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsApi {
    @GET("teams")
    suspend fun getTeams(
        @Query("league") league: String,
        @Query("season") season: Int = 2024
    ): TeamsResponse

    companion object {
        // Using API-SPORTS as an example
        const val BASE_URL = "https://v3.football.api-sports.io/"
        // You'll need to sign up for a free API key
        const val API_KEY = "deae9560aefb953c79444206ee9c17c7"
    }
}

data class TeamsResponse(
    val response: List<TeamResponse>
)

data class TeamResponse(
    val team: TeamData
)

data class TeamData(
    val id: Int,
    val name: String,
    val logo: String
) 