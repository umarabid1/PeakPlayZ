package com.example.peakplays.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TheSportsDBApi {

    @Headers("X-API-KEY: 002930")
    @GET("json/list/players/{id}")
    suspend fun getTeamPlayers(@Path("id") id: String): PlayersResponse

    @Headers("X-API-KEY: 002930")
    @GET("json/list/teams/{leagueId}")
    suspend fun getTeams(@Path("leagueId") leagueId: String): TeamsResponse

    @Headers("X-API-KEY: 002930")
    @GET("json/list/leagues")
    suspend fun getLeagues(): LeaguesResponse

    companion object {
        const val BASE_URL = "https://www.thesportsdb.com/api/v2/"
    }

    data class PlayersResponse(
        val list: List<PlayerData>?
    )

    data class PlayerData(
        val idPlayer: String?,
        val strPlayer: String?,
        val strTeam: String?,
        val strTeamid: String?,
        val strPosition: String?,
        val strNumber: String?,
        val strHeight: String?,
        val strWeight: String?,
        val strNationality: String?,
        val dateBorn: String?,
        val strLeague: String?,
        val strSport: String?,
        val strThumb: String?,
        val strCutout: String?,
        val strRender: String?
    )

    data class TeamsResponse(
        val list: List<TeamData>?
    )

    data class TeamData(
        val idTeam: String?,
        val strTeam: String?,
        val strLeague: String?
    )

    data class LeaguesResponse(
        val list: List<LeagueData>?
    )

    data class LeagueData(
        val idLeague: String?,
        val strLeague: String?
    )
}
