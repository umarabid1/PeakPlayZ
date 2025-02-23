package com.example.peakplays.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peakplays.api.ApiClient
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    private val _teamsMap = MutableStateFlow<Map<League, List<Team>>>(emptyMap())
    val teamsMap: StateFlow<Map<League, List<Team>>> = _teamsMap.asStateFlow()

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
                Team("Buffalo Bills", "https://example.com/bills.png", league),
                Team("Miami Dolphins", "https://example.com/dolphins.png", league),
                // ... other NFL teams
            )
            League.NBA -> listOf(
                Team("Boston Celtics", "https://example.com/celtics.png", league),
                Team("Brooklyn Nets", "https://example.com/nets.png", league),
                // ... other NBA teams
            )
            League.MLB -> listOf(
                Team("Arizona Diamondbacks", "https://example.com/diamondbacks.png", league),
                Team("Atlanta Braves", "https://example.com/braves.png", league),
                // ... other MLB teams
            )
            League.NHL -> listOf(
                Team("Anaheim Ducks", "https://example.com/ducks.png", league),
                Team("Arizona Coyotes", "https://example.com/coyotes.png", league),
                // ... other NHL teams
            )
            League.MLS -> listOf(
                Team("Atlanta United FC", "https://example.com/atlantaunited.png", league),
                Team("Austin FC", "https://example.com/austin.png", league),
                // ... other MLS teams
            )
            League.EPL -> listOf(
                Team("Arsenal", "https://example.com/arsenal.png", league),
                Team("Aston Villa", "https://example.com/astonvilla.png", league),
                // ... other EPL teams
            )
            League.BUNDESLIGA -> listOf(
                Team("FC Bayern Munich", "https://example.com/fcbayern.png", league),
                Team("Borussia Dortmund", "https://example.com/borussiadortmund.png", league),
                // ... other Bundesliga teams
            )
            League.SERIE_A -> listOf(
                Team("AC Milan", "https://example.com/acmilan.png", league),
                Team("AS Roma", "https://example.com/asroma.png", league),
                // ... other Serie A teams
            )
            League.LA_LIGA -> listOf(
                Team("Real Madrid", "https://example.com/realmadrid.png", league),
                Team("Barcelona", "https://example.com/barcelona.png", league),
                // ... other La Liga teams
            )
        }
    }
} 