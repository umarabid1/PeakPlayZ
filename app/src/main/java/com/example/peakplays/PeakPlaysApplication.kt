package com.example.peakplays

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.example.peakplays.utils.LocaleHelper
import com.google.firebase.FirebaseApp

class PeakPlaysApplication : Application() {
    private val TAG = "PeakPlaysApplication"

    override fun onCreate() {
        val prefs = getSharedPreferences(LocaleHelper.PREFS_NAME, Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString(LocaleHelper.PREF_LANGUAGE, null)
        Log.d(TAG, "onCreate - Saved language: $savedLanguage")
        Log.d(TAG, "onCreate - Current locale: ${resources.configuration.locales[0]}")
        
        if (savedLanguage != null) {
            LocaleHelper.setLocale(this)
        }
        
        super.onCreate()
        FirebaseApp.initializeApp(this)
        
        Log.d(TAG, "After onCreate - Current locale: ${resources.configuration.locales[0]}")
    }

    override fun attachBaseContext(base: Context) {
        Log.d(TAG, "attachBaseContext - Before: ${base.resources.configuration.locales[0]}")
        val context = LocaleHelper.setLocale(base)
        super.attachBaseContext(context)
        Log.d(TAG, "attachBaseContext - After: ${context.resources.configuration.locales[0]}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigurationChanged - Before: ${resources.configuration.locales[0]}")
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this)
        Log.d(TAG, "onConfigurationChanged - After: ${resources.configuration.locales[0]}")
    }
} 