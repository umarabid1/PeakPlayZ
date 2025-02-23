package com.example.peakplays.api

import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApi {
    @GET("searchteams.php")
    suspend fun searchTeam(@Query("t") teamName: String): TeamSearchResponse

    @GET("lookup_all_teams.php")
    suspend fun getTeamsByLeague(@Query("id") leagueId: String): TeamSearchResponse

    companion object {
        const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/002930/"
    }
}

data class TeamSearchResponse(
    val teams: List<TeamData>
)

data class TeamData(
    val idTeam: String,
    val strTeam: String,
    val strBadge: String,
    val strLeague: String
) 