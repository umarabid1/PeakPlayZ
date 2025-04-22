package com.example.peakplays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peakplays.adapters.LiveEventAdapter
import com.example.peakplays.api.ApiClient
import com.example.peakplays.databinding.FragmentHomeBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.LiveEvent
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up click listeners for all league buttons
        binding.button1.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.NFL)
            )
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.NBA)
            )
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.MLB)
            )
        }

        binding.button4.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.NHL)
            )
        }

        binding.button5.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.LA_LIGA)
            )
        }

        binding.button6.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.EPL)
            )
        }

        binding.button7.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.BUNDESLIGA)
            )
        }

        binding.button8.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.SERIE_A)
            )
        }

        binding.button9.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_league_details,
                LeagueDetailsFragment.createArguments(League.MLS)
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            setupLive()
        }









    }

    private suspend fun setupLive() {
        Log.d("TeamScheduleFragment", "running")




        // Try fetching the schedule from the API
        try {
            val response = ApiClient.sportsLiveApi.teamLive()
            val teamEvents = response.livescore.map { teamSch ->
                LiveEvent(
                    homeTeam = teamSch.strHomeTeam.toString(),
                    homeScore = teamSch.intHomeScore.toString(),
                    awayTeam = teamSch.strAwayTeam.toString(),
                    awayScore = teamSch.intAwayScore.toString()
                )
            }
            // Set up RecyclerView adapter with the API data
            binding.LiveList.layoutManager = LinearLayoutManager(requireContext())
            binding.LiveList.adapter = LiveEventAdapter(teamEvents)
            Log.d("TeamScheduleFragment", "Number of events: ${response.livescore.size}")
//            binding.navigationTeamSchedule.text= team.name


        } catch (e: Exception) {
            Log.d("TeamScheduleFragment", e.toString())
            // On error, use dummy data
//            val dummyResponse = getDummyTeamSchResponse()
//            val teamEvents = dummyResponse.events.map { teamSch ->
//                TeamEvent(
//                    name = teamSch.strEvent,
//                    date = teamSch.dateEvent
//                )
        }
        // Set up RecyclerView adapter with the dummy data
//            binding.LiveList.layoutManager = LinearLayoutManager(requireContext())
//            binding.LiveList.adapter = TeamEventAdapter(teamEvents)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


