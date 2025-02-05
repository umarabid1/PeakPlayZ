package com.example.peakplays.base

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.peakplays.utils.LocaleHelper
import java.util.*

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val languageCode = LocaleHelper.getLanguageCode(newBase)
        super.attachBaseContext(LocaleHelper.setLocale(newBase, languageCode))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val languageCode = LocaleHelper.getLanguageCode(this)
        LocaleHelper.setLocale(this, languageCode)
    }
} 