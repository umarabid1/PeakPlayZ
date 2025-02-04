package com.example.peakplays

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.peakplays.databinding.ActivityPasswordResetBinding

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
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

        // Setup reset password button
        binding.resetPasswordButton.setOnClickListener {
            // Here you would implement password reset logic
            finish()
        }
    }
} 