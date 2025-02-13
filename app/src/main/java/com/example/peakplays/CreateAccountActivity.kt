package com.example.peakplays

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Hide system UI navigation
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // Setup back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Setup create account button
        binding.createAccountButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val country = binding.countryInput.text.toString()

            if (validateInputs(email, password, firstName, lastName)) {
                createAccount(email, password)
            }
        }

        // Setup country dropdown
        val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, countries)
        (binding.countryInput as? AutoCompleteTextView)?.setAdapter(arrayAdapter)
    }

    private fun validateInputs(email: String, password: String, firstName: String, lastName: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Account created successfully, but don't sign in
                    Toast.makeText(this, "Account created successfully! Please sign in.", Toast.LENGTH_SHORT).show()
                    // Go back to sign in screen
                    finish()
                } else {
                    // If sign up fails, display a user-friendly message
                    val errorMessage = when {
                        task.exception?.message?.contains("email address is already in use") == true ->
                            "This email is already registered"
                        task.exception?.message?.contains("badly formatted") == true ->
                            "Please enter a valid email address"
                        else -> "Failed to create account. Please try again."
                    }
                    Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }
} 