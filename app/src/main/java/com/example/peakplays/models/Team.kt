package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val name: String,
    val logoUrl: String,
    val league: League,
    val id: String
) : Parcelable

// Remove any League enum definition if it exists here 