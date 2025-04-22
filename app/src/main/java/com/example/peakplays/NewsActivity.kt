package com.example.peakplays

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.view.WindowManager
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.GridLayout
import android.util.Log

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        supportActionBar?.hide()
        actionBar?.hide()

        setContentView(R.layout.activity_news)

        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        findViewById<TextView>(R.id.titleText)?.text = getString(R.string.latest_sports_news)

        setupClickListeners()

        // ❌ NO fragment loaded here immediately anymore!!
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
        val category = when (cardId) {
            R.id.nbaNewsCard -> "nba"
            R.id.nflNewsCard -> "nfl"
            R.id.mlbNewsCard -> "mlb"
            R.id.nhlNewsCard -> "nhl"
            R.id.soccerNewsCard -> "soccer"
            R.id.f1NewsCard -> "f1"
            else -> null
        }

        if (category != null) {
            openNewsFragment(category)
        } else {
            Toast.makeText(this, "News Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openNewsFragment(category: String) {
        val fragment = NewsFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        findViewById<GridLayout>(R.id.newsGrid).visibility = View.GONE
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
            findViewById<GridLayout>(R.id.newsGrid).visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}
