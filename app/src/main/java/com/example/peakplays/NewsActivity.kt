package com.example.peakplays

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.view.Window
import android.view.WindowManager
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.util.Log

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Modern way to handle window flags
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        
        // Make sure there's no action bar
        supportActionBar?.hide()
        actionBar?.hide()
        
        // Set content view after removing title
        setContentView(R.layout.activity_news)

        // Set window flags
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        // Use string resource for title
        findViewById<TextView>(R.id.titleText)?.text = getString(R.string.latest_sports_news)

        setupClickListeners()

        if (savedInstanceState == null) {
            Log.d("NewsActivity", "Creating new NewsFragment")
            val fragment = NewsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onTitleChanged(title: CharSequence?, color: Int) {
        super.onTitleChanged(getString(R.string.latest_sports_news), color)
    }

    override fun onResume() {
        super.onResume()
        // Set title again in onResume
        title = getString(R.string.latest_sports_news)
        actionBar?.title = getString(R.string.latest_sports_news)
        supportActionBar?.title = getString(R.string.latest_sports_news)
    }

    override fun onPostResume() {
        super.onPostResume()
        // Try one more time in onPostResume
        title = getString(R.string.latest_sports_news)
        actionBar?.title = getString(R.string.latest_sports_news)
        supportActionBar?.title = getString(R.string.latest_sports_news)
    }

    private fun setupClickListeners() {
        val cardIds = listOf(
            R.id.nbaNewsCard,
            R.id.nflNewsCard,
            R.id.mlbNewsCard,
            R.id.nhlNewsCard,
            R.id.soccerNewsCard,
            R.id.f1NewsCard
        )

        cardIds.forEach { id ->
            findViewById<CardView>(id).setOnClickListener { 
                handleNewsCardClick(id)
            }
        }
    }

    private fun handleNewsCardClick(cardId: Int) {
        val message = when (cardId) {
            R.id.nbaNewsCard -> "NBA News Coming Soon"
            R.id.nflNewsCard -> "NFL News Coming Soon"
            R.id.mlbNewsCard -> "MLB News Coming Soon"
            R.id.nhlNewsCard -> "NHL News Coming Soon"
            R.id.soccerNewsCard -> "Soccer News Coming Soon"
            R.id.f1NewsCard -> "F1 News Coming Soon"
            else -> "News Coming Soon"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
} 