package com.example.peakplays.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsApi {
    @GET("searchteams.php")
    suspend fun searchTeam(@Query("t") teamName: String): TeamSearchResponse

    @GET("lookup_all_teams.php")
    suspend fun getTeamsByLeague(@Query("id") leagueId: String): TeamSearchResponse

    @GET("eventsnext.php")
    suspend fun getTeamSch(@Query("id") teamId: String): TeamSchResponse


    //    @Headers("X-API-KEY: 3")
    @GET("v2_livescore_example.json")//livescore/{id}
    suspend fun teamLive(): TeamLiveRes //@Path("id") id: String

    companion object {
        const val BASE_URL =
            "https://www.thesportsdb.com/api/v1/json/002930/" ///api/v1/json/3/lookupteam.php?id=133604
        const val LIVE_URL =
            "https://www.thesportsdb.com/xml/"
        // "https://www.thesportsdb.com/api/v2/json/" ///api/v1/json/3/lookupteam.php?id=133604

    }



    data class TeamSearchResponse(
        val teams: List<TeamData>
    )

    data class TeamData(
        val idTeam: String,
        val strTeam: String,
        val strBadge: String,
        val strLeague: String,


        )


    data class TeamSchResponse(
        val events: List<TeamSch>
    )

    data class TeamSch(
        val idEvent: String,
        val strEvent: String,
        val dateEvent: String,

        )
    data class TeamLiveRes(
        val lives: List<LiveData>
    )

    data class LiveData(
        val intAwayScore: String,
        val strHomeTeam: String,
        val strAwayTeam: String,
        val intHomeScore: String,


        )
}

