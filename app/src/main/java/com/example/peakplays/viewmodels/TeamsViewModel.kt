package com.example.peakplays.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peakplays.api.ApiClient
import com.example.peakplays.databinding.FragmentTeamScheduleBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import com.example.peakplays.models.TeamEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    private var _binding: FragmentTeamScheduleBinding? = null
    private val binding get() = _binding!!
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
                            name = teamData.strTeam,
                            logoUrl = teamData.strBadge,
                            league = league,
                            id=teamData.idTeam
                        )
                    }

                    _teamsMap.value += (league to teams)
                } catch (e: Exception) {
                    // If API fails, use dummy data
                    _teamsMap.value += (league to getDummyTeams(league))
                }
            }

        }
    }

    private fun getDummyTeams(league: League): List<Team> {
        return when (league) {
            League.NFL -> listOf(
                Team(
                    "Buffalo Bills", "https://example.com/bills.png", league,
                    id = "11111"
                ),
                Team(
                    "Miami Dolphins", "https://example.com/dolphins.png", league,
                    id = TODO()
                ),
                // ... other NFL teams
            )
            League.NBA -> listOf(
                Team(
                    "Boston Celtics", "https://example.com/celtics.png", league,
                    id = TODO()
                ),
                Team(
                    "Brooklyn Nets", "https://example.com/nets.png", league,
                    id = TODO()
                ),
                // ... other NBA teams
            )
            League.MLB -> listOf(
                Team(
                    "Arizona Diamondbacks", "https://example.com/diamondbacks.png", league,
                    id = TODO()
                ),
                Team(
                    "Atlanta Braves", "https://example.com/braves.png", league,
                    id = TODO()
                ),
                // ... other MLB teams
            )
            League.NHL -> listOf(
                Team(
                    "Anaheim Ducks", "https://example.com/ducks.png", league,
                    id = TODO()
                ),
                Team(
                    "Arizona Coyotes", "https://example.com/coyotes.png", league,
                    id = TODO()
                ),
                // ... other NHL teams
            )
            League.MLS -> listOf(
                Team(
                    "Atlanta United FC", "https://example.com/atlantaunited.png", league,
                    id = TODO()
                ),
                Team(
                    "Austin FC", "https://example.com/austin.png", league,
                    id = TODO()
                ),
                // ... other MLS teams
            )
            League.EPL -> listOf(
                Team(
                    "Arsenal", "https://example.com/arsenal.png", league,
                    id = TODO()
                ),
                Team(
                    "Aston Villa", "https://example.com/astonvilla.png", league,
                    id = TODO()
                ),
                // ... other EPL teams
            )
            League.BUNDESLIGA -> listOf(
                Team(
                    "FC Bayern Munich", "https://example.com/fcbayern.png", league,
                    id = TODO()
                ),
                Team(
                    "Borussia Dortmund", "https://example.com/borussiadortmund.png", league,
                    id = TODO()
                ),
                // ... other Bundesliga teams
            )
            League.SERIE_A -> listOf(
                Team(
                    "AC Milan", "https://example.com/acmilan.png", league,
                    id = TODO()
                ),
                Team(
                    "AS Roma", "https://example.com/asroma.png", league,
                    id = TODO()
                ),
                // ... other Serie A teams
            )
            League.LA_LIGA -> listOf(
                Team(
                    "Real Madrid", "https://example.com/realmadrid.png", league,
                    id = TODO()
                ),
                Team(
                    "Barcelona", "https://example.com/barcelona.png", league,
                    id = TODO()
                ),
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