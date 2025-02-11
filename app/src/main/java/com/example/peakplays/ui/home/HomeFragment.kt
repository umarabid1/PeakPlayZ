package com.example.peakplays.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import java.util.Locale
import com.example.peakplays.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        resources.configuration.setLocale(locale)
    }

    @Suppress("unused")
    private fun getLanguageCode(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("language_code", "en") ?: "en"
    }

    @Suppress("unused")
    private fun saveLanguageCode(context: Context, languageCode: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString("language_code", languageCode).apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 