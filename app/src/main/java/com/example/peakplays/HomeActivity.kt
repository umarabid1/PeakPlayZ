package com.example.peakplays

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.peakplays.base.BaseActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.peakplays.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import android.util.Log
import android.widget.Button

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isLeaguesExpanded = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Hide only system UI navigation, keep app's bottom navigation visible
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add window insets listener to handle navigation bar height
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            binding.bottomNavigation.setPadding(0, 0, 0, navigationInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
        
        // Configure bottom navigation for different screen sizes
        if (resources.configuration.screenWidthDp >= 1240) {
            bottomNavigation.layoutParams = bottomNavigation.layoutParams.apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

        // Setup leagues bar click listener
        binding.leaguesBar?.setOnClickListener {
            isLeaguesExpanded = !isLeaguesExpanded
            binding.leaguesContent?.visibility = if (isLeaguesExpanded) View.VISIBLE else View.GONE
            binding.dropdownIcon?.rotation = if (isLeaguesExpanded) 180f else 0f
        }

        // Update navigation listener to also handle leagues content
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isHome = destination.id == R.id.navigation_home
            binding.scoreBar?.visibility = if (isHome) View.VISIBLE else View.GONE
            binding.leaguesBar?.visibility = if (isHome) View.VISIBLE else View.GONE
            if (!isHome) {
                binding.leaguesContent?.visibility = View.GONE
                binding.dropdownIcon?.rotation = 0f
                isLeaguesExpanded = false
            }
        }

        // Update time every minute
        updateTime()
        startTimeUpdates()

        binding.settingsButton?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.signInButton?.setOnClickListener {
            if (auth.currentUser != null) {
                // User is signed in, so sign them out
                auth.signOut()
                Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
                updateSignInButton()
            } else {
                // User is not signed in, go to sign in screen
                startActivity(Intent(this, SignInActivity::class.java))
            }
        }
    }

    private fun updateTime() {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
            // Override the AM/PM markers to always use "am" and "pm"
            val symbols = java.text.DateFormatSymbols(Locale.getDefault())
            symbols.amPmStrings = arrayOf("am", "pm")
            dateFormatSymbols = symbols
        }
        findViewById<TextView>(R.id.timeText).text = timeFormat.format(Date())
    }

    private fun startTimeUpdates() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                updateTime()
                handler.postDelayed(this, 60000) // Update every minute
            }
        })
    }

    private fun updateSignInButton() {
        // Update button text based on auth state
        binding.signInButton?.text = if (auth.currentUser != null) {
            getString(R.string.sign_out)
        } else {
            getString(R.string.sign_in)
        }
    }

    override fun onResume() {
        super.onResume()
        updateSignInButton()
    }
}