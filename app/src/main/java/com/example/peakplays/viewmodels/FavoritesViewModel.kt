package com.example.peakplays.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val _favoriteTeams = MutableStateFlow<Set<String>>(setOf())
    val favoriteTeams: StateFlow<Set<String>> = _favoriteTeams.asStateFlow()

    init {
        // Load saved favorites
        _favoriteTeams.value = sharedPrefs.getStringSet("favorite_teams", setOf()) ?: setOf()
    }

    fun toggleFavorite(teamId: String) {
        val currentFavorites = _favoriteTeams.value.toMutableSet()
        if (teamId in currentFavorites) {
            currentFavorites.remove(teamId)
        } else {
            currentFavorites.add(teamId)
        }
        _favoriteTeams.value = currentFavorites

        // Save to SharedPreferences
        sharedPrefs.edit().putStringSet("favorite_teams", currentFavorites).apply()
    }

    fun isFavorite(teamId: String): Boolean {
        return teamId in _favoriteTeams.value
    }
} 