package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class League(val displayName: String, val apiId: String) : Parcelable {
    NFL("NFL", "4391"),
    NBA("NBA", "4387"),
    MLB("MLB", "4424"),
    NHL("NHL", "4380"),
    MLS("MLS", "4346"),
    EPL("Premier League", "4328"),
    BUNDESLIGA("Bundesliga", "4331"),
    SERIE_A("Serie A", "4332"),
    LA_LIGA("La Liga", "4335")
} 