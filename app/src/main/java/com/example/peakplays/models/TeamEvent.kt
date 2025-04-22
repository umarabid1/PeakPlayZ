package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamEvent(
    val name: String,
    val date: String,


    ) : Parcelable

@Parcelize
data class LiveEvent(
    val homeTeam: String,
    val homeScore: String,
    val awayTeam: String,
    val awayScore: String


    ) : Parcelable