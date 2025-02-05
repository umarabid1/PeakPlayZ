package com.example.peakplays

import android.app.Application
import android.content.Context
import com.example.peakplays.utils.LocaleHelper

class App : Application() {
    override fun attachBaseContext(base: Context) {
        val languageCode = LocaleHelper.getLanguageCode(base)
        super.attachBaseContext(LocaleHelper.setLocale(base, languageCode))
    }
} 