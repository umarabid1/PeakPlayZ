package com.example.peakplays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peakplays.databinding.FragmentHomeBinding
import com.example.peakplays.models.League

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}