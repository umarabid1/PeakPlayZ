package com.example.peakplays.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import java.util.Locale

object LocaleHelper {
    const val PREFS_NAME = "settings"
    const val PREF_LANGUAGE = "language_code"
    private const val TAG = "LocaleHelper"

    fun setLocale(context: Context): Context {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val language = prefs.getString(PREF_LANGUAGE, null) 
            ?: context.resources.configuration.locales[0].language

        Log.d(TAG, "Setting locale - Saved preference: ${prefs.getString(PREF_LANGUAGE, null)}")
        Log.d(TAG, "Setting locale - Current language: $language")
        Log.d(TAG, "Setting locale - Current locale: ${context.resources.configuration.locales[0]}")

        val locale = when (language) {
            "zh" -> Locale.SIMPLIFIED_CHINESE
            "ja" -> Locale.JAPAN
            "ko" -> Locale.KOREA
            else -> Locale(language)
        }

        Log.d(TAG, "Setting locale - New locale: $locale")
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }

        val newContext = context.createConfigurationContext(config)
        Log.d(TAG, "Final context locale: ${newContext.resources.configuration.locales[0]}")
        return newContext
    }

    fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getString(PREF_LANGUAGE, null)
        val default = context.resources.configuration.locales[0].language
        Log.d(TAG, "Getting language code - Saved: $saved, Default: $default")
        return saved ?: default
    }

    fun saveLanguageCode(context: Context, languageCode: String) {
        Log.d(TAG, "Saving language code: $languageCode")
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(PREF_LANGUAGE, languageCode)
            .commit()  // Use commit() instead of apply() to ensure immediate write
        
        // Verify the save
        val savedValue = prefs.getString(PREF_LANGUAGE, null)
        Log.d(TAG, "Verified saved language code: $savedValue")

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        Log.d(TAG, "After save - Current locale: ${context.resources.configuration.locales[0]}")
    }
} 