package com.example.peakplays

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Hide system UI navigation
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // Setup back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Setup sign-in button
        binding.signInButton.setOnClickListener {
            val email = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (validateInput(email, password)) {
                signInWithEmailAndPassword(email, password)
            }
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

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.usernameInput.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameInput.error = "Enter a valid email"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.passwordInput.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign-in success
                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Close the sign-in activity
                } else {
                    // Sign-in failed
                    Toast.makeText(this, "Sign-in failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}

