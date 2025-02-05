package com.example.peakplays.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LocaleHelper {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
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