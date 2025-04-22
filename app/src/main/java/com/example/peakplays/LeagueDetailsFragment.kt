package com.example.peakplays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.peakplays.databinding.FragmentLeagueDetailsBinding
import com.example.peakplays.models.League

class LeagueDetailsFragment : Fragment() {
    private var _binding: FragmentLeagueDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeagueDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        arguments?.getParcelable<League>("league")?.let { league ->
            setupLeagueDetails(league)
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupLeagueDetails(league: League) {
        binding.leagueName.text = league.displayName
        
        val logoResId = when (league) {
            League.NFL -> R.drawable.ic_nfl
            League.NBA -> R.drawable.ic_nba
            League.MLB -> R.drawable.ic_mlb
            League.NHL -> R.drawable.ic_nhl
            League.MLS -> R.drawable.ic_mls
            League.EPL -> R.drawable.ic_epl
            League.BUNDESLIGA -> R.drawable.ic_bl
            League.SERIE_A -> R.drawable.ic_seriea
            League.LA_LIGA -> R.drawable.ic_laliga
        }
        binding.leagueLogo.setImageResource(logoResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArguments(league: League) = Bundle().apply {
            putParcelable("league", league)
        }
    }
} 