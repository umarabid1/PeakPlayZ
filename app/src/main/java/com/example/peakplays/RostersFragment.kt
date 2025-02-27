package com.example.peakplays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peakplays.adapters.TeamAdapter
import com.example.peakplays.databinding.FragmentRostersBinding
import com.example.peakplays.databinding.LeagueSectionBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import com.example.peakplays.api.ApiClient
import com.example.peakplays.viewmodels.TeamsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import androidx.navigation.fragment.findNavController

class RostersFragment : Fragment() {
    private var _binding: FragmentRostersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TeamsViewModel by viewModels()
    private val leagueSections = mutableMapOf<League, LeagueSectionBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("RostersFragment", "onCreateView called")
        _binding = FragmentRostersBinding.inflate(inflater, container, false)
        setupLeagueSections()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RostersFragment", "onViewCreated called")
    }

    private fun setupLeagueSections() {
        binding.leaguesContainer.removeAllViews()
        
        viewLifecycleOwner.lifecycleScope.launch {
            League.values().forEach { league ->
                val sectionBinding = LeagueSectionBinding.inflate(
                    layoutInflater,
                    binding.leaguesContainer,
                    true
                )
                
                with(sectionBinding) {
                    leagueHeader.text = league.displayName
                    
                    teamsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        visibility = View.GONE
                    }
                    
                    var isExpanded = false
                    headerContainer.setOnClickListener {
                        isExpanded = !isExpanded
                        teamsRecyclerView.visibility = if (isExpanded) View.VISIBLE else View.GONE
                        expandIcon.animate()
                            .rotation(if (isExpanded) 180f else 0f)
                            .setDuration(200)
                            .start()
                        
                        if (isExpanded && teamsRecyclerView.adapter == null) {
                            progressBar.visibility = View.VISIBLE
                            
                            // Use the pre-loaded teams from ViewModel
                            val teams = viewModel.teamsMap.value[league]
                            if (!teams.isNullOrEmpty()) {
                                progressBar.visibility = View.GONE
                                teamsRecyclerView.adapter = TeamAdapter(teams) { team ->
                                    findNavController().navigate(
                                        R.id.navigation_team_roster,
                                        TeamRosterFragment.createArguments(team)
                                    )
                                }
                            }
                        }
                    }
                }
                
                leagueSections[league] = sectionBinding
            }

            // Observe teams updates
            viewModel.teamsMap.collect { teamsMap ->
                teamsMap.forEach { (league, teams) ->
                    leagueSections[league]?.let { sectionBinding ->
                        if (sectionBinding.teamsRecyclerView.visibility == View.VISIBLE) {
                            sectionBinding.progressBar.visibility = View.GONE
                            sectionBinding.teamsRecyclerView.adapter = TeamAdapter(teams) { team ->
                                findNavController().navigate(
                                    R.id.navigation_team_roster,
                                    TeamRosterFragment.createArguments(team)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 