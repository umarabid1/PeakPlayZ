package com.example.peakplays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.peakplays.R
import com.example.peakplays.adapters.PlayerAdapter
import com.example.peakplays.api.TheSportsDBService
import com.example.peakplays.databinding.FragmentTeamRosterBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import com.example.peakplays.viewmodels.FavoritesViewModel
import com.example.peakplays.viewmodels.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle

class TeamRosterFragment : Fragment() {
    private var _binding: FragmentTeamRosterBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()
    private lateinit var playerAdapter: PlayerAdapter
    private val sportsDBService = TheSportsDBService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TeamRosterFragment", "onCreateView called")
        _binding = FragmentTeamRosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TeamRosterFragment", "onViewCreated called")

        setupRecyclerView()
        setupObservers()
        setupBackButton()

        // Get team from arguments
        arguments?.getParcelable<Team>("team")?.let { team ->
            Log.d("TeamRosterFragment", "Team received: ${team.name}, ID: ${team.id}, League: ${team.league}")
            teamViewModel.setSelectedTeam(team)
            
            // Use the correct team ID for NFL teams
            val teamId = if (team.league == League.NFL) {
                // NFL team IDs are different in the API
                when (team.name) {
                    "Arizona Cardinals" -> "134946"
                    else -> team.id
                }
            } else {
                team.id
            }
            
            Log.d("TeamRosterFragment", "Using team ID: $teamId for API call")
            loadTeamRoster(teamId)
        } ?: run {
            Log.e("TeamRosterFragment", "No team data received in arguments")
            Toast.makeText(requireContext(), "Error: No team data received", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        Log.d("TeamRosterFragment", "Setting up RecyclerView")
        playerAdapter = PlayerAdapter()
        
        binding.playersRecyclerView.apply {
            Log.d("TeamRosterFragment", "Configuring RecyclerView")
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
            visibility = View.VISIBLE
            setHasFixedSize(true)
            // Add a divider between items
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            Log.d("TeamRosterFragment", "RecyclerView configuration complete")
        }

        // Add this line to verify the adapter is attached
        Log.d("TeamRosterFragment", "Adapter attached: ${binding.playersRecyclerView.adapter != null}")
    }

    private fun setupObservers() {
        Log.d("TeamRosterFragment", "Setting up observers")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                teamViewModel.selectedTeam.collect { team: Team? ->
                    Log.d("TeamRosterFragment", "Team observer triggered: ${team?.name}")
                    team?.let { updateTeamHeader(it) }
                }
            }
        }
    }

    private fun setupBackButton() {
        Log.d("TeamRosterFragment", "Setting up back button")
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateTeamHeader(team: Team) {
        Log.d("TeamRosterFragment", "Updating team header for: ${team.name}")
        binding.apply {
            teamName.text = team.name
            
            // Load team logo using Glide
            Glide.with(requireContext())
                .load(team.logoUrl)
                .placeholder(R.drawable.team_logo_placeholder)
                .error(R.drawable.team_logo_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(teamLogo)
        }
    }

    private fun loadTeamRoster(teamId: String) {
        Log.d("TeamRosterFragment", "Starting to load team roster for team ID: $teamId")
        binding.apply {
            progressBar.visibility = View.VISIBLE
            playersRecyclerView.visibility = View.GONE
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("TeamRosterFragment", "Making API call for team ID: $teamId")
                val players = sportsDBService.getTeamRoster(teamId)
                Log.d("TeamRosterFragment", "Successfully fetched ${players.size} players")
                if (players.isNotEmpty()) {
                    Log.d("TeamRosterFragment", "First player: ${players[0].name}, Last player: ${players[players.size - 1].name}")
                }
                
                withContext(Dispatchers.Main) {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        playersRecyclerView.visibility = View.VISIBLE
                        
                        if (players.isEmpty()) {
                            Log.w("TeamRosterFragment", "No players received from API")
                            Toast.makeText(context, "No players found for this team", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("TeamRosterFragment", "Updating adapter with ${players.size} players")
                            playerAdapter.updatePlayers(players)
                            // Force a layout pass
                            playersRecyclerView.post {
                                playersRecyclerView.requestLayout()
                                Log.d("TeamRosterFragment", "Layout requested after data update")
                                // Add this to verify the RecyclerView state
                                Log.d("TeamRosterFragment", "RecyclerView state - Height: ${playersRecyclerView.height}, " +
                                    "Width: ${playersRecyclerView.width}, " +
                                    "Visibility: ${playersRecyclerView.visibility == View.VISIBLE}, " +
                                    "Item count: ${playerAdapter.itemCount}")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TeamRosterFragment", "Error loading roster", e)
                withContext(Dispatchers.Main) {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        playersRecyclerView.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, "Failed to load team roster: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
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