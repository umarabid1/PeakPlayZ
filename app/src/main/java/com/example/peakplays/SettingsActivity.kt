package com.example.peakplays

import android.os.Bundle
import android.view.View
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivitySettingsBinding
import com.example.peakplays.ui.settings.SettingsFragment

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }

        // Set up back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Hide system UI navigation
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
} 