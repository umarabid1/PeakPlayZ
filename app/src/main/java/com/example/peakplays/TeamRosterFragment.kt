package com.example.peakplays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.peakplays.databinding.FragmentTeamRosterBinding
import com.example.peakplays.models.Team
import com.example.peakplays.viewmodels.FavoritesViewModel
import kotlinx.coroutines.launch

class TeamRosterFragment : Fragment() {
    private var _binding: FragmentTeamRosterBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

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
            // Use navigateUp() instead of popBackStack()
            findNavController().navigateUp()
        }
    }

    private fun setupTeamRoster(team: Team) {
        binding.teamName.text = team.name

        // Set up favorite button
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

        // Load team logo using Glide
        Glide.with(requireContext())
            .load(team.logoUrl)
            .placeholder(R.drawable.team_logo_placeholder)
            .error(R.drawable.team_logo_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.teamLogo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArguments(team: Team) = Bundle().apply {
            putParcelable("team", team)
        }
    }
}