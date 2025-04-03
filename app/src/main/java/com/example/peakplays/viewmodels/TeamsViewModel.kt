package com.example.peakplays.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peakplays.api.ApiClient
import com.example.peakplays.models.League
import com.example.peakplays.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    private val _teamsMap = MutableStateFlow<Map<League, List<Team>>>(emptyMap())
    val teamsMap: StateFlow<Map<League, List<Team>>> = _teamsMap.asStateFlow()

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam

    init {
        preloadAllTeams()
    }

    private fun preloadAllTeams() {
        viewModelScope.launch {
            League.values().forEach { league ->
                try {
                    val response = ApiClient.sportsApi.getTeamsByLeague(league.apiId)
                    val teams = response.teams.map { teamData ->
                        Team(
                            id = teamData.idTeam,
                            name = teamData.strTeam,
                            logoUrl = teamData.strBadge,
                            league = league
                        )
                    }
                    
                    _teamsMap.value = _teamsMap.value + (league to teams)
                } catch (e: Exception) {
                    // If API fails, use dummy data
                    _teamsMap.value = _teamsMap.value + (league to getDummyTeams(league))
                }
            }
        }
    }

    private fun getDummyTeams(league: League): List<Team> {
        return when (league) {
            League.NFL -> listOf(
                Team("NFL_BUF", "Buffalo Bills", "https://example.com/bills.png", league),
                Team("NFL_MIA", "Miami Dolphins", "https://example.com/dolphins.png", league),
                // ... other NFL teams
            )
            League.NBA -> listOf(
                Team("NBA_BOS", "Boston Celtics", "https://example.com/celtics.png", league),
                Team("NBA_BKN", "Brooklyn Nets", "https://example.com/nets.png", league),
                // ... other NBA teams
            )
            League.MLB -> listOf(
                Team("MLB_ARI", "Arizona Diamondbacks", "https://example.com/diamondbacks.png", league),
                Team("MLB_ATL", "Atlanta Braves", "https://example.com/braves.png", league),
                // ... other MLB teams
            )
            League.NHL -> listOf(
                Team("NHL_ANA", "Anaheim Ducks", "https://example.com/ducks.png", league),
                Team("NHL_ARI", "Arizona Coyotes", "https://example.com/coyotes.png", league),
                // ... other NHL teams
            )
            League.MLS -> listOf(
                Team("MLS_ATL", "Atlanta United FC", "https://example.com/atlantaunited.png", league),
                Team("MLS_AUS", "Austin FC", "https://example.com/austin.png", league),
                // ... other MLS teams
            )
            League.EPL -> listOf(
                Team("EPL_ARS", "Arsenal", "https://example.com/arsenal.png", league),
                Team("EPL_AVL", "Aston Villa", "https://example.com/astonvilla.png", league),
                // ... other EPL teams
            )
            League.BUNDESLIGA -> listOf(
                Team("BUN_BAY", "FC Bayern Munich", "https://example.com/fcbayern.png", league),
                Team("BUN_DOR", "Borussia Dortmund", "https://example.com/borussiadortmund.png", league),
                // ... other Bundesliga teams
            )
            League.SERIE_A -> listOf(
                Team("SER_MIL", "AC Milan", "https://example.com/acmilan.png", league),
                Team("SER_ROM", "AS Roma", "https://example.com/asroma.png", league),
                // ... other Serie A teams
            )
            League.LA_LIGA -> listOf(
                Team("LIG_MAD", "Real Madrid", "https://example.com/realmadrid.png", league),
                Team("LIG_BAR", "Barcelona", "https://example.com/barcelona.png", league),
                // ... other La Liga teams
            )
        }
    }

    fun clearSelectedTeam() {
        _selectedTeam.value = null
    }

    fun setSelectedTeam(team: Team) {
        _selectedTeam.value = team
    }
} 