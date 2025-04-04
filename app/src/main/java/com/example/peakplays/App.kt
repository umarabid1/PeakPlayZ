package com.example.peakplays

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.peakplays.utils.LocaleHelper

class App : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this)
    }
}