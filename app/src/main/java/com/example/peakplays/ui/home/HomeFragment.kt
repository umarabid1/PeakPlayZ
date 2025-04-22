package com.example.peakplays.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.peakplays.databinding.FragmentHomeBinding
import com.google.android.material.button.MaterialButton
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        for (i in 1..9) {
            val buttonId = resources.getIdentifier("button$i", "id", requireContext().packageName)
            view?.findViewById<MaterialButton>(buttonId)?.setOnClickListener {
                // Handle button click
                Toast.makeText(context, "League $i selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        
        // Replace deprecated calls with newer API
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // If these functions aren't being used, either remove them or mark them for future use
    @Suppress("unused")
    private fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return prefs.getString("language_code", "en") ?: "en"
    }

    @Suppress("unused")
    private fun saveLanguageCode(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        prefs.edit().putString("language_code", languageCode).apply()
    }
} 