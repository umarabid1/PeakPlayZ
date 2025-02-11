package com.example.peakplays

import android.app.Application
import com.google.firebase.FirebaseApp

class PeakPlaysApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 