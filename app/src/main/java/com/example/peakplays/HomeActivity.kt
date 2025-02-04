package com.example.peakplays

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.peakplays.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isLeaguesExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
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
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun updateTime() {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
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
}