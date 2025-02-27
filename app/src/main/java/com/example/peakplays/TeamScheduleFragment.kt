package com.example.peakplays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peakplays.databinding.FragmentTeamScheduleBinding
import com.example.peakplays.models.Team
import com.bumptech.glide.Glide

class TeamScheduleFragment : Fragment() {
    private var _binding: FragmentTeamScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamScheduleBinding.inflate(inflater, container, false)
        
        setupBackButton()
        
        // Get team from arguments
        arguments?.getParcelable<Team>("team")?.let { team ->
            setupTeamSchedule(team)
        }
        
        return binding.root
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupTeamSchedule(team: Team) {
        binding.teamNameHeader.text = team.name
        
        // Load team logo
        Glide.with(this)
            .load(team.logoUrl)
            .into(binding.teamLogo)
            
        // TODO: Add schedule-specific implementation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArguments(team: Team): Bundle {
            return Bundle().apply {
                putParcelable("team", team)
            }
        }
    }
} 