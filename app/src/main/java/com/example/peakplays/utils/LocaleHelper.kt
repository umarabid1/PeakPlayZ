package com.example.peakplays.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleHelper {
    private const val PREFS_NAME = "settings"
    private const val PREF_LANGUAGE = "language_code"

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

        val config = context.resources.configuration
        config.setLocale(locale)
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }

    fun getLanguageCode(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(PREF_LANGUAGE, "en") ?: "en"
    }

    fun saveLanguageCode(context: Context, languageCode: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_LANGUAGE, languageCode)
            .apply()
    }
} 