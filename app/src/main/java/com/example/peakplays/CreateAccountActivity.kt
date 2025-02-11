package com.example.peakplays

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.peakplays.base.BaseActivity
import com.example.peakplays.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
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

        // Setup create account button
        binding.createAccountButton.setOnClickListener {
            // Here you would implement account creation logic
            finish()
        }

        // Setup country dropdown
        val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, countries)
        (binding.countryInput as? AutoCompleteTextView)?.setAdapter(arrayAdapter)
    }
} 