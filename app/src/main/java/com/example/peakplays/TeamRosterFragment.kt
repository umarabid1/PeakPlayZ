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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.peakplays.adapters.PlayerAdapter
import com.example.peakplays.api.TheSportsDBService
import com.example.peakplays.databinding.FragmentTeamRosterBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import com.example.peakplays.viewmodels.FavoritesViewModel
import com.example.peakplays.viewmodels.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import javax.inject.Inject
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class TeamRosterFragment : Fragment() {
    private var _binding: FragmentTeamRosterBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()
    private lateinit var playerAdapter: PlayerAdapter

    @Inject
    lateinit var sportsDBService: TheSportsDBService

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

            // Get the correct team ID based on the league
            val teamId = when (team.league) {
                League.NFL -> getNFLTeamId(team.name)
                League.NBA -> getNBATeamId(team.name)
                League.MLB -> getMLBTeamId(team.name)
                League.NHL -> getNHLTeamId(team.name)
                else -> team.id
            }

            Log.d("TeamRosterFragment", "Using team ID: $teamId for API call")
            loadTeamRoster(team.name, team.id.toString(),team.league.toString())
        } ?: run {
            Log.e("TeamRosterFragment", "No team data received in arguments")
            Toast.makeText(requireContext(), "Error: No team data received", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNFLTeamId(teamName: String): String {
        return when (teamName) {
            "Arizona Cardinals" -> "134946"
            "Atlanta Falcons" -> "134947"
            "Baltimore Ravens" -> "134948"
            "Buffalo Bills" -> "134949"
            "Carolina Panthers" -> "134940"
            "Chicago Bears" -> "134941"
            "Cincinnati Bengals" -> "134942"
            "Cleveland Browns" -> "134943"
            "Dallas Cowboys" -> "134944"
            "Denver Broncos" -> "134945"
            "Detroit Lions" -> "134934"
            "Green Bay Packers" -> "134935"
            "Houston Texans" -> "134936"
            "Indianapolis Colts" -> "134937"
            "Jacksonville Jaguars" -> "134938"
            "Kansas City Chiefs" -> "134939"
            "Las Vegas Raiders" -> "134928"
            "Los Angeles Chargers" -> "134929"
            "Los Angeles Rams" -> "134930"
            "Miami Dolphins" -> "134931"
            "Minnesota Vikings" -> "134932"
            "New England Patriots" -> "134933"
            "New Orleans Saints" -> "134922"
            "New York Giants" -> "134923"
            "New York Jets" -> "134924"
            "Philadelphia Eagles" -> "134925"
            "Pittsburgh Steelers" -> "134926"
            "San Francisco 49ers" -> "134927"
            "Seattle Seahawks" -> "134916"
            "Tampa Bay Buccaneers" -> "134917"
            "Tennessee Titans" -> "134918"
            "Washington Commanders" -> "134919"
            else -> "134946" // Default to Cardinals if team not found
        }
    }

    private fun getNBATeamId(teamName: String): String {
        return when (teamName) {
            "Atlanta Hawks" -> "134880"
            "Boston Celtics" -> "134881"
            "Brooklyn Nets" -> "134882"
            "Charlotte Hornets" -> "134883"
            "Chicago Bulls" -> "134884"
            "Cleveland Cavaliers" -> "134885"
            "Dallas Mavericks" -> "134886"
            "Denver Nuggets" -> "134887"
            "Detroit Pistons" -> "134888"
            "Golden State Warriors" -> "134889"
            "Houston Rockets" -> "134890"
            "Indiana Pacers" -> "134891"
            "Los Angeles Clippers" -> "134892"
            "Los Angeles Lakers" -> "134893"
            "Memphis Grizzlies" -> "134894"
            "Miami Heat" -> "134895"
            "Milwaukee Bucks" -> "134896"
            "Minnesota Timberwolves" -> "134897"
            "New Orleans Pelicans" -> "134898"
            "New York Knicks" -> "134899"
            "Oklahoma City Thunder" -> "134900"
            "Orlando Magic" -> "134901"
            "Philadelphia 76ers" -> "134902"
            "Phoenix Suns" -> "134903"
            "Portland Trail Blazers" -> "134904"
            "Sacramento Kings" -> "134905"
            "San Antonio Spurs" -> "134906"
            "Toronto Raptors" -> "134907"
            "Utah Jazz" -> "134908"
            "Washington Wizards" -> "134909"
            else -> "134880" // Default to Hawks if team not found
        }
    }

    private fun getMLBTeamId(teamName: String): String {
        return when (teamName) {
            "Arizona Diamondbacks" -> "133614"
            "Atlanta Braves" -> "133615"
            "Baltimore Orioles" -> "133616"
            "Boston Red Sox" -> "133617"
            "Chicago Cubs" -> "133618"
            "Chicago White Sox" -> "133619"
            "Cincinnati Reds" -> "133620"
            "Cleveland Guardians" -> "133621"
            "Colorado Rockies" -> "133622"
            "Detroit Tigers" -> "133623"
            "Houston Astros" -> "133624"
            "Kansas City Royals" -> "133625"
            "Los Angeles Angels" -> "133626"
            "Los Angeles Dodgers" -> "133627"
            "Miami Marlins" -> "133628"
            "Milwaukee Brewers" -> "133629"
            "Minnesota Twins" -> "133630"
            "New York Mets" -> "133631"
            "New York Yankees" -> "133632"
            "Oakland Athletics" -> "133633"
            "Philadelphia Phillies" -> "133634"
            "Pittsburgh Pirates" -> "133635"
            "San Diego Padres" -> "133636"
            "San Francisco Giants" -> "133637"
            "Seattle Mariners" -> "133638"
            "St. Louis Cardinals" -> "133639"
            "Tampa Bay Rays" -> "133640"
            "Texas Rangers" -> "133641"
            "Toronto Blue Jays" -> "133642"
            "Washington Nationals" -> "133643"
            else -> "133614" // Default to Diamondbacks if team not found
        }
    }

    private fun getNHLTeamId(teamName: String): String {
        return when (teamName) {
            "Anaheim Ducks" -> "134845"
            "Arizona Coyotes" -> "134846"
            "Boston Bruins" -> "134847"
            "Buffalo Sabres" -> "134848"
            "Calgary Flames" -> "134849"
            "Carolina Hurricanes" -> "134850"
            "Chicago Blackhawks" -> "134851"
            "Colorado Avalanche" -> "134852"
            "Columbus Blue Jackets" -> "134853"
            "Dallas Stars" -> "134854"
            "Detroit Red Wings" -> "134855"
            "Edmonton Oilers" -> "134856"
            "Florida Panthers" -> "134857"
            "Los Angeles Kings" -> "134858"
            "Minnesota Wild" -> "134859"
            "Montreal Canadiens" -> "134860"
            "Nashville Predators" -> "134861"
            "New Jersey Devils" -> "134862"
            "New York Islanders" -> "134863"
            "New York Rangers" -> "134864"
            "Ottawa Senators" -> "134865"
            "Philadelphia Flyers" -> "134866"
            "Pittsburgh Penguins" -> "134867"
            "San Jose Sharks" -> "134868"
            "Seattle Kraken" -> "134869"
            "St. Louis Blues" -> "134870"
            "Tampa Bay Lightning" -> "134871"
            "Toronto Maple Leafs" -> "134872"
            "Vancouver Canucks" -> "134873"
            "Vegas Golden Knights" -> "134874"
            "Washington Capitals" -> "134875"
            "Winnipeg Jets" -> "134876"
            else -> "134845" // Default to Ducks if team not found
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
            findNavController().navigateUp()
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

    private fun loadTeamRoster(teamName: String, idd: String,league: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val players = sportsDBService.getTeamRoster(teamName, idd,league)
                if (players.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_players_found, teamName),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                playerAdapter.updatePlayers(players)
                Log.d("TeamRosterFragment", "Successfully loaded ${players.size} players for $teamName")
            } catch (e: Exception) {
                Log.e("TeamRosterFragment", "Error loading roster", e)
                Toast.makeText(
                    requireContext(),
                    R.string.error_loading_roster,
                    Toast.LENGTH_SHORT
                ).show()
                playerAdapter.updatePlayers(emptyList())
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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