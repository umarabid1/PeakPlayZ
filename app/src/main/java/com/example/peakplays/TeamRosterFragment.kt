package com.example.peakplays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.peakplays.databinding.FragmentTeamRosterBinding
import com.example.peakplays.models.Team

class TeamRosterFragment : Fragment() {
    private var _binding: FragmentTeamRosterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamRosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Get team from arguments
        arguments?.getParcelable<Team>("team")?.let { team ->
            setupTeamRoster(team)
        }

        binding.backButton.setOnClickListener {
            // Navigate back
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupTeamRoster(team: Team) {
        binding.teamName.text = team.name
        // RecyclerView and adapter setup will be done when database is ready
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(team: Team) = TeamRosterFragment().apply {
            arguments = Bundle().apply {
                putParcelable("team", team)
            }
        }
    }
} 