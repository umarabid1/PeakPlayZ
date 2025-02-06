package com.example.peakplays.ui.settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    init {
        _notificationsEnabled.value = sharedPrefs.getBoolean("notifications_enabled", false)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean("notifications_enabled", enabled).commit()
        _notificationsEnabled.value = enabled
    }
}