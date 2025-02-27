package com.example.peakplays

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.peakplays.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var isHighlightsExpanded = false
    private var isNotificationsEnabled = false

    // Request permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            enableNotifications()
        } else {
            Toast.makeText(context, 
                "Notification permission denied. You won't receive sports updates.", 
                Toast.LENGTH_LONG).show()
            updateBellIcon(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup initial bell icon state
        updateBellIcon(isNotificationsEnabled)

        // Setup highlights section click listener
        binding.highlightsHeader.setOnClickListener {
            isHighlightsExpanded = !isHighlightsExpanded
            binding.highlightsContent.visibility = if (isHighlightsExpanded) View.VISIBLE else View.GONE
            binding.highlightsDropdownIcon.rotation = if (isHighlightsExpanded) 180f else 0f
        }

        // Add notification bell click listener
        binding.notificationBell.setOnClickListener {
            if (isNotificationsEnabled) {
                // If notifications are enabled, disable them
                disableNotifications()
            } else {
                // If notifications are disabled, check permission and enable
                checkNotificationPermission()
            }
        }
    }

    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, enable notifications
                enableNotifications()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                // Show explanation why we need notification permission
                Toast.makeText(context, 
                    "We need notification permission to send you sports updates.", 
                    Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            else -> {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun enableNotifications() {
        isNotificationsEnabled = true
        updateBellIcon(true)
        Toast.makeText(context, 
            "Notifications enabled! You'll receive sports updates.", 
            Toast.LENGTH_SHORT).show()
    }

    private fun disableNotifications() {
        isNotificationsEnabled = false
        updateBellIcon(false)
        Toast.makeText(context, 
            "Notifications disabled.", 
            Toast.LENGTH_SHORT).show()
    }

    private fun updateBellIcon(enabled: Boolean) {
        binding.notificationBell.apply {
            alpha = if (enabled) 1.0f else 0.5f  // Darken when enabled
            setColorFilter(
                if (enabled) 
                    ContextCompat.getColor(context, android.R.color.white)
                else 
                    ContextCompat.getColor(context, R.color.white_30_percent)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 