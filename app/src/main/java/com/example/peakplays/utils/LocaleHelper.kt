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

        Log.d(TAG, "Setting locale - Requested language: $language")

        val locale = createLocale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        }

        val newContext = context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return newContext
    }

    private fun createLocale(language: String): Locale {
        return when (language) {
            "zh" -> Locale.SIMPLIFIED_CHINESE
            "ja" -> Locale.JAPAN
            "ko" -> Locale.KOREA
            else -> Locale(language)
        }
    }

    fun saveLanguageCode(context: Context, languageCode: String) {
        Log.d(TAG, "Saving language code: $languageCode")
        
        // Save the preference
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_LANGUAGE, languageCode)
            .apply()  // Changed from commit() to apply() for better performance

        // Update the configuration immediately
        val locale = createLocale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        }

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_LANGUAGE, null) 
            ?: context.resources.configuration.locales[0].language
    }
} 