package com.example.peakplays

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivitySettingsBinding
import com.example.peakplays.utils.LocaleHelper
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showNotificationPermissionDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide system UI navigation
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // Setup back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Setup notification switch
        binding.notificationSwitch.apply {
            isChecked = getNotificationPreference()
            
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    requestNotificationPermission()
                } else {
                    saveNotificationPreference(false)
                }
            }
        }

        // Setup language selector
        setupLanguageSelector()
    }

    private fun requestNotificationPermission() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                // For Android 8.0 (API level 26) and above, direct to notification settings
                showNotificationPermissionDialog()
            }
            else -> {
                // For older versions, just save the preference
                saveNotificationPreference(true)
            }
        }
    }

    private fun showNotificationPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Enable Notifications")
            .setMessage("To receive updates and important information, please enable notifications for this app in Settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton("Not Now") { dialog, _ ->
                binding.notificationSwitch.isChecked = false
                saveNotificationPreference(false)
                dialog.dismiss()
            }
            .show()
    }

    private fun openNotificationSettings() {
        val intent = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
            }
            else -> {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:$packageName")
                }
            }
        }
        startActivity(intent)
    }

    private fun getNotificationPreference(): Boolean {
        return getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getBoolean("notifications_enabled", true)
    }

    private fun saveNotificationPreference(enabled: Boolean) {
        getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("notifications_enabled", enabled)
            .apply()
    }

    private fun setupLanguageSelector() {
        val languages = resources.getStringArray(R.array.languages)
        val languageCodes = resources.getStringArray(R.array.language_codes)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        
        (binding.languageSelector as? AutoCompleteTextView)?.apply {
            setAdapter(arrayAdapter)
            
            // Set current language
            val currentCode = LocaleHelper.getLanguageCode(this@SettingsActivity)
            val currentIndex = languageCodes.indexOf(currentCode)
            if (currentIndex >= 0) {
                setText(languages[currentIndex], false)
            }

            setOnItemClickListener { _, _, position, _ ->
                val selectedCode = languageCodes[position]
                if (selectedCode != LocaleHelper.getLanguageCode(this@SettingsActivity)) {
                    LocaleHelper.saveLanguageCode(this@SettingsActivity, selectedCode)
                    // Update all activities
                    val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    // Add this method to update UI text
    private fun updateUIText() {
        binding.apply {
            // Update title in the app bar
            appBarLayout.findViewById<TextView>(R.id.titleText)?.text = getString(R.string.settings)
            // Update notifications text
            notificationSwitch.text = getString(R.string.enable_notifications)
            // Update language section title
            languageTitle.text = getString(R.string.language_title)
            // Update language selector hint
            languageSelector.hint = getString(R.string.select_language)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUIText()
    }
} 