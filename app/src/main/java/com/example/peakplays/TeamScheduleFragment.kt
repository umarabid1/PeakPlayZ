package com.example.peakplays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peakplays.databinding.FragmentTeamScheduleBinding
import com.example.peakplays.models.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peakplays.adapters.TeamEventAdapter
import com.example.peakplays.viewmodels.FavoritesViewModel
import com.example.peakplays.api.ApiClient
import com.example.peakplays.api.SportsApi.TeamSch
import com.example.peakplays.api.SportsApi.TeamSchResponse
import com.example.peakplays.models.TeamEvent


class TeamScheduleFragment : Fragment() {
    private var _binding: FragmentTeamScheduleBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate and return the binding root
        _binding = FragmentTeamScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onViewCreated is the right place to set up UI logic after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()

        // Safely get the Team object from fragment arguments
        arguments?.getParcelable<Team>("team")?.let { team ->
            // Launch a coroutine in the lifecycle scope to call the suspend function
            viewLifecycleOwner.lifecycleScope.launch {
                setupTeamSchedule(team)
            }
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    // This function returns dummy data matching the JSON structure you provided
    private fun getDummyTeamSchResponse(): TeamSchResponse {
        return TeamSchResponse(
            events = listOf(
                TeamSch(
                    idEvent = "2070137",
                    strEvent = "Liverpool vs Everton",
                    dateEvent = "2025-04-02"
                ),
                TeamSch(
                    idEvent = "2070142",
                    strEvent = "Fulham vs Liverpool",
                    dateEvent = "2025-04-06"
                ),
                TeamSch(
                    idEvent = "2070152",
                    strEvent = "Liverpool vs West Ham",
                    dateEvent = "2025-04-13"
                ),
                TeamSch(
                    idEvent = "2070164",
                    strEvent = "Leicester vs Liverpool",
                    dateEvent = "2025-04-20"
                ),
                TeamSch(
                    idEvent = "2070172",
                    strEvent = "Liverpool vs Tottenham",
                    dateEvent = "2025-04-27"
                )
            )
        )
    }

    // This function sets up the team schedule, fetching data from the API or using dummy data
    private suspend fun setupTeamSchedule(team: Team) {
        binding.teamNameHeader.text = team.name
//        binding.navigationTeamSchedule.text= team.name

        // Observe favorites in a coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.favoriteTeams.collect { favorites ->
                val isFavorite = team.name in favorites
                binding.favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.ic_star_filled
                    else R.drawable.ic_star_outline
                )
            }
        }

        binding.favoriteButton.setOnClickListener {
            favoritesViewModel.toggleFavorite(team.name)
        }

        // Load team logo with Glide
        Glide.with(requireContext())
            .load(team.logoUrl)
            .placeholder(R.drawable.team_logo_placeholder)
            .error(R.drawable.team_logo_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.teamLogo)

        // Try fetching the schedule from the API
        try {
            val response = ApiClient.sportsApi.getTeamSch(team.id)
            val teamEvents = response.events.map { teamSch ->
                TeamEvent(
                    name = teamSch.strEvent,
                    date = teamSch.dateEvent
                )
            }
            // Set up RecyclerView adapter with the API data
            binding.navigationTeamScheduleList.layoutManager = LinearLayoutManager(requireContext())
            binding.navigationTeamScheduleList.adapter = TeamEventAdapter(teamEvents)
            Log.d("TeamScheduleFragment", "Number of events: ${response.events.size}")
//            binding.navigationTeamSchedule.text= team.name


        } catch (e: Exception) {
            // On error, use dummy data
            val dummyResponse = getDummyTeamSchResponse()
            val teamEvents = dummyResponse.events.map { teamSch ->
                TeamEvent(
                    name = teamSch.strEvent,
                    date = teamSch.dateEvent
                )
            }
            // Set up RecyclerView adapter with the dummy data
            binding.navigationTeamScheduleList.layoutManager = LinearLayoutManager(requireContext())
            binding.navigationTeamScheduleList.adapter = TeamEventAdapter(teamEvents)
        }
    }

    companion object {
        fun createArguments(team: Team): Bundle {
            return Bundle().apply {
                putParcelable("team", team)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
