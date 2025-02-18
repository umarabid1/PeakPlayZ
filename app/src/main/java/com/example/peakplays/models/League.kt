package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class League(val displayName: String, val apiId: String) : Parcelable {
    NFL("NFL", "nfl"),
    NBA("NBA", "nba"),
    MLB("MLB", "mlb"),
    NHL("NHL", "nhl"),
    MLS("Major League Soccer", "mls"),
    EPL("Premier League", "39"),
    BUNDESLIGA("Bundesliga", "78"),
    SERIE_A("Serie A", "135"),
    UFC("UFC", "ufc")
} 