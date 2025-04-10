package com.example.peakplays.models

import android.os.Parcel
import android.os.Parcelable

data class Player(
    val id: String?,
    val name: String?,
    val position: String,
    val number: String,
    val teamId: String,
    val age: Int? = null,
    val weight: String,
    val height: String,
    val nationality: String? = null,
    val dateOfBirth: String? = null,
    val teamName: String? = null,
    val league: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "Unknown",
        parcel.readString() ?: "N/A",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString() ?: "N/A",
        parcel.readString() ?: "N/A",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(position)
        parcel.writeString(number)
        parcel.writeString(teamId)
        parcel.writeValue(age)
        parcel.writeString(weight)
        parcel.writeString(height)
        parcel.writeString(nationality)
        parcel.writeString(dateOfBirth)
        parcel.writeString(teamName)
        parcel.writeString(league)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
} 