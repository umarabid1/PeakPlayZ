package com.example.peakplays.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.peakplays.utils.LocaleHelper

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase))
    }
} 