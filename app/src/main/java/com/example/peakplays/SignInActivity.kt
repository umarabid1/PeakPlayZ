package com.example.peakplays

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
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

        // Setup sign in button (no backend implementation)
        binding.signInButton.setOnClickListener {
            // Here you would implement the actual sign in logic
            finish() // For now, just close the activity
        }

        // Setup forgot password button
        binding.forgotPasswordButton.setOnClickListener {
            startActivity(Intent(this, PasswordResetActivity::class.java))
        }

        // Setup create account button
        binding.createAccountButton.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }

    // Add this method to update UI text when language changes
    override fun onResume() {
        super.onResume()
        updateUIText()
    }

    private fun updateUIText() {
        binding.apply {
            // Update title in the app bar
            appBarLayout.findViewById<TextView>(R.id.titleText)?.text = getString(R.string.sign_in_title)
            // Update buttons
            signInButton.text = getString(R.string.sign_in)
            forgotPasswordButton.text = getString(R.string.forgot_password)
            createAccountButton.text = getString(R.string.create_account_prompt)
        }
    }
} 