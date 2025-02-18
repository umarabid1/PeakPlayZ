package com.example.peakplays

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.view.Window
import android.view.WindowManager

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide the title bar completely
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        
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

        // Find and update the title in the custom toolbar
        findViewById<TextView>(R.id.titleText)?.text = "Latest in Sports News"

        setupClickListeners()
    }

    override fun onTitleChanged(title: CharSequence?, color: Int) {
        super.onTitleChanged("Latest in Sports News", color)
    }

    override fun onResume() {
        super.onResume()
        // Set title again in onResume
        title = "Latest in Sports News"
        actionBar?.title = "Latest in Sports News"
        supportActionBar?.title = "Latest in Sports News"
    }

    override fun onPostResume() {
        super.onPostResume()
        // Try one more time in onPostResume
        title = "Latest in Sports News"
        actionBar?.title = "Latest in Sports News"
        supportActionBar?.title = "Latest in Sports News"
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