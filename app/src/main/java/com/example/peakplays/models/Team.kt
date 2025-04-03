package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: String,
    val name: String,
    val logoUrl: String,
    val league: League
) : Parcelable

// Remove any League enum definition if it exists here 