package com.example.peakplays.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamEvent(
    val name: String,
    val date: String,


    ) : Parcelable