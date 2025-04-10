package com.example.peakplays.viewmodels

import androidx.lifecycle.ViewModel
import com.example.peakplays.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamViewModel : ViewModel() {
    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam.asStateFlow()

    fun setSelectedTeam(team: Team) {
        _selectedTeam.value = team
    }

    fun clearSelectedTeam() {
        _selectedTeam.value = null
    }
} 