package com.example.peakplays.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {
    private const val PREFS_NAME = "app_preferences"
    private const val PREF_LANGUAGE = "selected_language"

    fun setLocale(context: Context): Context {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val language = prefs.getString(PREF_LANGUAGE, "en") ?: "en"
        return updateResources(context, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = when (language) {
            "zh" -> Locale.CHINESE
            "ja" -> Locale.JAPANESE
            "ko" -> Locale.KOREAN
            else -> Locale(language)
        }
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    fun getLanguageCode(context: Context): String {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language_code", "en") ?: "en"
    }

    fun saveLanguageCode(context: Context, languageCode: String) {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putString("language_code", languageCode)
            .apply()
    }
} 