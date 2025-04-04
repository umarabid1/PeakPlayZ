package com.example.peakplays.utils

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import android.util.Log
import java.util.Locale
import android.app.Activity
import android.content.Intent
import com.example.peakplays.MainActivity

object LocaleHelper {
    const val PREFS_NAME = "settings"
    const val PREF_LANGUAGE = "language_code"
    private const val TAG = "LocaleHelper"

    @JvmStatic
    @Suppress("unused")
    fun setLocale(context: Context): Context {
        val languageCode = getLanguageCode(context)
        return updateResources(context, languageCode)
    }

    private fun updateResources(context: Context, languageCode: String): Context {
        val locale = createLocale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)

        return context.createConfigurationContext(config)
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

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_LANGUAGE, languageCode)
            .apply()
    }

    fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_LANGUAGE, null)
            ?: context.resources.configuration.locales[0].language
    }

    @Suppress("unused")
    fun setLocale(activity: Activity, languageCode: String) {
        // Save the new language code
        saveLanguageCode(activity, languageCode)

        // Update the locale
        val locale = createLocale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)

        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)
        activity.createConfigurationContext(config)

        // Restart the app completely
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
        activity.finish()
    }
} 