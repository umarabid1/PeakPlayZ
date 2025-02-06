package com.example.peakplays.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.peakplays.R
import com.example.peakplays.databinding.FragmentSettingsBinding
import java.util.Locale
import android.widget.Toast

class SettingsFragment : Fragment() {

    init {
        Log.e("SettingsFragment", "Fragment instance created")
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SettingsFragment"
    private val PREFS_NAME = "app_preferences"
    private val PREF_NOTIFICATIONS = "notifications_enabled"
    private val PREF_LANGUAGE = "selected_language"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "SettingsFragment created")
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        
        // Load saved preference
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val allPrefs = prefs.all // Get all preferences for debugging
        Log.d(TAG, "onCreateView - All preferences: $allPrefs")
        
        val isEnabled = prefs.getBoolean(PREF_NOTIFICATIONS, false)
        Log.d(TAG, "onCreateView - Loading notification preference: $isEnabled")
        
        binding.notificationsSwitch.isChecked = isEnabled

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, "Switch toggled to: $isChecked")
            if (isChecked) {
                if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
                    showEnableNotificationsDialog()
                } else {
                    saveNotificationPreference(true)
                }
            } else {
                saveNotificationPreference(false)
                showDisableNotificationsDialog()
            }
        }

        setupLanguageSelector()

        return binding.root
    }

    private fun setupLanguageSelector() {
        val languages = resources.getStringArray(R.array.languages)
        val languageCodes = resources.getStringArray(R.array.language_codes)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.languageSelector.setAdapter(adapter)

        // Load saved language preference
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedLanguageCode = prefs.getString(PREF_LANGUAGE, languageCodes[0])
        val savedLanguageIndex = languageCodes.indexOf(savedLanguageCode)
        if (savedLanguageIndex >= 0) {
            binding.languageSelector.setText(languages[savedLanguageIndex], false)
        }

        binding.languageSelector.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguageCode = languageCodes[position]
            if (selectedLanguageCode != savedLanguageCode) {
                saveLanguagePreference(selectedLanguageCode)
                // Post the recreate call to allow the dropdown to close properly
                binding.root.post {
                    updateAppLanguage(selectedLanguageCode)
                }
            }
            binding.languageSelector.dismissDropDown()
        }

        // Prevent the dropdown from showing automatically when clicking outside
        binding.languageSelector.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.languageSelector.dismissDropDown()
            }
        }
    }

    private fun saveLanguagePreference(languageCode: String) {
        requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_LANGUAGE, languageCode)
            .apply()
    }

    private fun updateAppLanguage(languageCode: String) {
        val locale = when (languageCode) {
            "zh" -> Locale.CHINESE
            "ja" -> Locale.JAPANESE
            "ko" -> Locale.KOREAN
            else -> Locale(languageCode)
        }

        Locale.setDefault(locale)
        val context = requireContext()
        val config = context.resources.configuration
        config.setLocale(locale)
        
        // Create new context with updated configuration
        val newContext = context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Show a brief message that language is changing
        Toast.makeText(context, getString(R.string.changing_language), Toast.LENGTH_SHORT).show()

        // Use a handler to post the restart
        Handler(Looper.getMainLooper()).postDelayed({
            // Restart the entire app instead of just the activity
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.let { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            } ?: run {
                // If getLaunchIntentForPackage returns null, fallback to recreating the activity
                requireActivity().recreate()
            }
        }, 500)
    }

    private fun saveNotificationPreference(enabled: Boolean) {
        Log.d(TAG, "Saving notification preference: $enabled")
        try {
            val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(PREF_NOTIFICATIONS, enabled)
            val success = editor.commit()
            
            // Verify the save worked by reading it back
            val savedValue = prefs.getBoolean(PREF_NOTIFICATIONS, !enabled) // Use opposite as default to ensure we read the saved value
            Log.d(TAG, "Save completed - success=$success, verified value=$savedValue")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving preference", e)
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val allPrefs = prefs.all // Get all preferences for debugging
            Log.d(TAG, "onResume - All preferences: $allPrefs")
            
            val isEnabled = prefs.getBoolean(PREF_NOTIFICATIONS, false)
            Log.d(TAG, "onResume - Loading notification preference: $isEnabled")
            binding.notificationsSwitch.isChecked = isEnabled
        } catch (e: Exception) {
            Log.e(TAG, "Error loading preferences in onResume", e)
        }
    }

    private fun showEnableNotificationsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.enable_notifications)
            .setMessage(R.string.enable_notifications_message)
            .setPositiveButton(R.string.settings) { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                binding.notificationsSwitch.isChecked = false
                saveNotificationPreference(false)
            }
            .show()
    }

    private fun showDisableNotificationsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.disable_notifications)
            .setMessage(R.string.disable_notifications_message)
            .setPositiveButton(R.string.settings) { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton(R.string.keep_enabled) { _, _ ->
                binding.notificationsSwitch.isChecked = true
                saveNotificationPreference(true)
            }
            .show()
    }

    private fun openNotificationSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}