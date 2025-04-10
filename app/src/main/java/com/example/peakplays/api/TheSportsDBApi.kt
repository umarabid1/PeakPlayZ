package com.example.peakplays.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDBApi {
    @GET("lookupteam.php")
    suspend fun getTeamDetails(@Query("id") teamId: String): TeamResponse

    @GET("lookup_all_players.php")
    suspend fun getTeamPlayers(@Query("id") teamId: String): PlayersResponse

    @GET("searchplayers.php")
    suspend fun searchPlayers(
        @Query("t") teamName: String,
        @Query("l") league: String
    ): PlayersResponse

    data class PlayersResponse(
        val player: List<PlayerData>?
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
        val strSport: String?
    )

    data class TeamResponse(
        val teams: List<TeamData>?
    )

    data class TeamData(
        val idTeam: String?,
        val strTeam: String?,
        val strLeague: String?
    )

    companion object {
        const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/"
        
        // League IDs
        const val NFL_LEAGUE_ID = "4391"
        const val NBA_LEAGUE_ID = "4387"
        const val MLB_LEAGUE_ID = "4424"
        const val NHL_LEAGUE_ID = "4380"
        const val LALIGA_LEAGUE_ID = "4335"
        const val BUNDESLIGA_LEAGUE_ID = "4334"
        const val PREMIER_LEAGUE_ID = "4328"
        const val SERIE_A_LEAGUE_ID = "4332"
        const val MLS_LEAGUE_ID = "4346"
    }
}

data class TeamDetailsResponse(
    val teams: List<SportsDBTeamData>?
)

data class SportsDBTeamData(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strLeague: String?,
    val strSport: String?,
    val strTeamShort: String?,
    val strAlternate: String?,
    val intFormedYear: String?,
    val strStadium: String?,
    val strStadiumThumb: String?,
    val strStadiumDescription: String?,
    val strStadiumLocation: String?,
    val intStadiumCapacity: String?,
    val strWebsite: String?,
    val strFacebook: String?,
    val strTwitter: String?,
    val strInstagram: String?,
    val strDescriptionEN: String?,
    val strGender: String?,
    val strCountry: String?,
    val strTeamJersey: String?,
    val strTeamLogo: String?,
    val strTeamFanart1: String?,
    val strTeamFanart2: String?,
    val strTeamFanart3: String?,
    val strTeamFanart4: String?,
    val strTeamBanner: String?,
    val strYoutube: String?,
    val strLocked: String?
) 