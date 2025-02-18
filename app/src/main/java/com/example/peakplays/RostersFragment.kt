package com.example.peakplays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peakplays.adapters.TeamAdapter
import com.example.peakplays.databinding.FragmentRostersBinding
import com.example.peakplays.databinding.LeagueSectionBinding
import com.example.peakplays.models.League
import com.example.peakplays.models.Team
import com.example.peakplays.api.ApiClient
import kotlinx.coroutines.launch

class RostersFragment : Fragment() {
    private var _binding: FragmentRostersBinding? = null
    private val binding get() = _binding!!

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

    private suspend fun fetchTeams(league: League): List<Team> {
        return try {
            when (league) {
                League.NFL, League.NBA, League.MLB, League.NHL, League.MLS -> {
                    // Use dummy data for non-football leagues for now
                    getDummyTeams(league)
                }
                League.EPL, League.BUNDESLIGA, League.SERIE_A -> {
                    // Use API for football leagues
                    val response = ApiClient.sportsApi.getTeams(league.apiId)
                    response.response.map { teamResponse ->
                        Team(
                            name = teamResponse.team.name,
                            logoUrl = teamResponse.team.logo,
                            league = league
                        )
                    }
                }
                League.UFC -> getDummyTeams(league) // Use dummy data for UFC
            }
        } catch (e: Exception) {
            Log.e("RostersFragment", "Error fetching teams for ${league.name}", e)
            // Fallback to dummy data if API fails
            getDummyTeams(league)
        }
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
                    
                    // Setup RecyclerView
                    teamsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        visibility = View.GONE
                    }
                    
                    // Setup click listener for expansion
                    var isExpanded = false // Track expansion state
                    headerContainer.setOnClickListener {
                        isExpanded = !isExpanded // Toggle state
                        teamsRecyclerView.visibility = if (isExpanded) View.VISIBLE else View.GONE
                        expandIcon.animate()
                            .rotation(if (isExpanded) 180f else 0f)
                            .setDuration(200)
                            .start()
                        
                        if (isExpanded && teamsRecyclerView.adapter == null) {
                            // Show dummy teams immediately while fetching from API
                            val dummyTeams = getDummyTeams(league)
                            teamsRecyclerView.adapter = TeamAdapter(dummyTeams) { team ->
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, TeamRosterFragment.newInstance(team))
                                    .addToBackStack(null)
                                    .commit()
                            }
                            
                            // Try to fetch real teams in background
                            viewLifecycleOwner.lifecycleScope.launch {
                                try {
                                    val teams = fetchTeams(league)
                                    if (teams.isNotEmpty()) {
                                        teamsRecyclerView.adapter = TeamAdapter(teams) { team ->
                                            parentFragmentManager.beginTransaction()
                                                .replace(R.id.fragment_container, TeamRosterFragment.newInstance(team))
                                                .addToBackStack(null)
                                                .commit()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e("RostersFragment", "Error fetching teams", e)
                                }
                            }
                        }
                    }
                }
                
                leagueSections[league] = sectionBinding
            }
        }
    }

    private fun getDummyTeams(league: League): List<Team> {
        return when (league) {
            League.NFL -> listOf(
                Team("Buffalo Bills", "https://example.com/bills.png", league),
                Team("Miami Dolphins", "https://example.com/dolphins.png", league),
                Team("Arizona Cardinals", "https://example.com/cardinals.png", league),
                Team("Atlanta Falcons", "https://example.com/falcons.png", league),
                Team("Baltimore Ravens", "https://example.com/ravens.png", league),
                Team("Carolina Panthers", "https://example.com/panthers.png", league),
                Team("Chicago Bears", "https://example.com/bears.png", league),
                Team("Cincinnati Bengals", "https://example.com/bengals.png", league),
                Team("Cleveland Browns", "https://example.com/browns.png", league),
                Team("Dallas Cowboys", "https://example.com/cowboys.png", league),
                Team("Denver Broncos", "https://example.com/broncos.png", league),
                Team("Detroit Lions", "https://example.com/lions.png", league),
                Team("Green Bay Packers", "https://example.com/packers.png", league),
                Team("Houston Texans", "https://example.com/texans.png", league),
                Team("Indianapolis Colts", "https://example.com/colts.png", league),
                Team("Jacksonville Jaguars", "https://example.com/jaguars.png", league),
                Team("Kansas City Chiefs", "https://example.com/chiefs.png", league),
                Team("Las Vegas Raiders", "https://example.com/raiders.png", league),
                Team("Los Angeles Chargers", "https://example.com/chargers.png", league),
                Team("Los Angeles Rams", "https://example.com/rams.png", league),
                Team("Minnesota Vikings", "https://example.com/vikings.png", league),
                Team("New England Patriots", "https://example.com/patriots.png", league),
                Team("New Orleans Saints", "https://example.com/saints.png", league),
                Team("New York Giants", "https://example.com/giants.png", league),
                Team("New York Jets", "https://example.com/jets.png", league),
                Team("Philadelphia Eagles", "https://example.com/eagles.png", league),
                Team("Pittsburgh Steelers", "https://example.com/steelers.png", league),
                Team("San Francisco 49ers", "https://example.com/49ers.png", league),
                Team("Seattle Seahawks", "https://example.com/seahawks.png", league),
                Team("Tampa Bay Buccaneers", "https://example.com/buccaneers.png", league),
                Team("Tennessee Titans", "https://example.com/titans.png", league),
                Team("Washington Commanders", "https://example.com/commanders.png", league)
            )
            League.NBA -> listOf(
                Team("Boston Celtics", "https://example.com/celtics.png", league),
                Team("Brooklyn Nets", "https://example.com/nets.png", league),
                Team("Charlotte Hornets", "https://example.com/hornets.png", league),
                Team("Chicago Bulls", "https://example.com/bulls.png", league),
                Team("Cleveland Cavaliers", "https://example.com/cavaliers.png", league),
                Team("Dallas Mavericks", "https://example.com/mavericks.png", league),
                Team("Denver Nuggets", "https://example.com/nuggets.png", league),
                Team("Detroit Pistons", "https://example.com/pistons.png", league),
                Team("Golden State Warriors", "https://example.com/warriors.png", league),
                Team("Houston Rockets", "https://example.com/rockets.png", league),
                Team("Indiana Pacers", "https://example.com/pacers.png", league),
                Team("Los Angeles Clippers", "https://example.com/clippers.png", league),
                Team("Los Angeles Lakers", "https://example.com/lakers.png", league),
                Team("Memphis Grizzlies", "https://example.com/grizzlies.png", league),
                Team("Miami Heat", "https://example.com/heat.png", league),
                Team("Milwaukee Bucks", "https://example.com/bucks.png", league),
                Team("Minnesota Timberwolves", "https://example.com/timberwolves.png", league),
                Team("New Orleans Pelicans", "https://example.com/pelicans.png", league),
                Team("New York Knicks", "https://example.com/knicks.png", league),
                Team("Oklahoma City Thunder", "https://example.com/thunder.png", league),
                Team("Orlando Magic", "https://example.com/magic.png", league),
                Team("Philadelphia 76ers", "https://example.com/76ers.png", league),
                Team("Phoenix Suns", "https://example.com/suns.png", league),
                Team("Portland Trail Blazers", "https://example.com/blazers.png", league),
                Team("Sacramento Kings", "https://example.com/kings.png", league),
                Team("San Antonio Spurs", "https://example.com/spurs.png", league),
                Team("Toronto Raptors", "https://example.com/raptors.png", league),
                Team("Utah Jazz", "https://example.com/jazz.png", league),
                Team("Washington Wizards", "https://example.com/wizards.png", league)
            )
            League.MLB -> listOf(
                Team("Arizona Diamondbacks", "https://example.com/diamondbacks.png", league),
                Team("Atlanta Braves", "https://example.com/braves.png", league),
                Team("Baltimore Orioles", "https://example.com/orioles.png", league),
                Team("Boston Red Sox", "https://example.com/redsox.png", league),
                Team("Chicago Cubs", "https://example.com/cubs.png", league),
                Team("Chicago White Sox", "https://example.com/whitesox.png", league),
                Team("Cincinnati Reds", "https://example.com/reds.png", league),
                Team("Cleveland Guardians", "https://example.com/guardians.png", league),
                Team("Colorado Rockies", "https://example.com/rockies.png", league),
                Team("Detroit Tigers", "https://example.com/tigers.png", league),
                Team("Houston Astros", "https://example.com/astros.png", league),
                Team("Kansas City Royals", "https://example.com/royals.png", league),
                Team("Los Angeles Angels", "https://example.com/angels.png", league),
                Team("Los Angeles Dodgers", "https://example.com/dodgers.png", league),
                Team("Miami Marlins", "https://example.com/marlins.png", league),
                Team("Milwaukee Brewers", "https://example.com/brewers.png", league),
                Team("Minnesota Twins", "https://example.com/twins.png", league),
                Team("New York Mets", "https://example.com/mets.png", league),
                Team("New York Yankees", "https://example.com/yankees.png", league),
                Team("Oakland Athletics", "https://example.com/athletics.png", league),
                Team("Philadelphia Phillies", "https://example.com/phillies.png", league),
                Team("Pittsburgh Pirates", "https://example.com/pirates.png", league),
                Team("San Diego Padres", "https://example.com/padres.png", league),
                Team("San Francisco Giants", "https://example.com/giants.png", league),
                Team("Seattle Mariners", "https://example.com/mariners.png", league),
                Team("St. Louis Cardinals", "https://example.com/cardinals.png", league),
                Team("Tampa Bay Rays", "https://example.com/rays.png", league),
                Team("Texas Rangers", "https://example.com/rangers.png", league),
                Team("Toronto Blue Jays", "https://example.com/bluejays.png", league),
                Team("Washington Nationals", "https://example.com/nationals.png", league)
            )
            League.NHL -> listOf(
                Team("Anaheim Ducks", "https://example.com/ducks.png", league),
                Team("Arizona Coyotes", "https://example.com/coyotes.png", league),
                Team("Boston Bruins", "https://example.com/bruins.png", league),
                Team("Buffalo Sabres", "https://example.com/sabres.png", league),
                Team("Calgary Flames", "https://example.com/flames.png", league),
                Team("Carolina Hurricanes", "https://example.com/hurricanes.png", league),
                Team("Chicago Blackhawks", "https://example.com/blackhawks.png", league),
                Team("Colorado Avalanche", "https://example.com/avalanche.png", league),
                Team("Columbus Blue Jackets", "https://example.com/bluejackets.png", league),
                Team("Dallas Stars", "https://example.com/stars.png", league),
                Team("Detroit Red Wings", "https://example.com/redwings.png", league),
                Team("Edmonton Oilers", "https://example.com/oilers.png", league),
                Team("Florida Panthers", "https://example.com/panthers.png", league),
                Team("Los Angeles Kings", "https://example.com/kings.png", league),
                Team("Minnesota Wild", "https://example.com/wild.png", league),
                Team("Montreal Canadiens", "https://example.com/canadiens.png", league),
                Team("Nashville Predators", "https://example.com/predators.png", league),
                Team("New Jersey Devils", "https://example.com/devils.png", league),
                Team("New York Islanders", "https://example.com/islanders.png", league),
                Team("New York Rangers", "https://example.com/rangers.png", league),
                Team("Ottawa Senators", "https://example.com/senators.png", league),
                Team("Philadelphia Flyers", "https://example.com/flyers.png", league),
                Team("Pittsburgh Penguins", "https://example.com/penguins.png", league),
                Team("San Jose Sharks", "https://example.com/sharks.png", league),
                Team("Seattle Kraken", "https://example.com/kraken.png", league),
                Team("St. Louis Blues", "https://example.com/blues.png", league),
                Team("Tampa Bay Lightning", "https://example.com/lightning.png", league),
                Team("Toronto Maple Leafs", "https://example.com/mapleleafs.png", league),
                Team("Vancouver Canucks", "https://example.com/canucks.png", league),
                Team("Vegas Golden Knights", "https://example.com/goldenknights.png", league),
                Team("Washington Capitals", "https://example.com/capitals.png", league),
                Team("Winnipeg Jets", "https://example.com/jets.png", league)
            )
            League.MLS -> listOf(
                Team("Atlanta United FC", "https://example.com/atlantaunited.png", league),
                Team("Austin FC", "https://example.com/austin.png", league),
                Team("Charlotte FC", "https://example.com/charlotte.png", league),
                Team("Chicago Fire FC", "https://example.com/chicagofire.png", league),
                Team("Colorado Rapids", "https://example.com/coloradorapids.png", league),
                Team("Columbus Crew", "https://example.com/columbuscrew.png", league),
                Team("D.C. United", "https://example.com/dcunited.png", league),
                Team("FC Cincinnati", "https://example.com/fccincinnati.png", league),
                Team("FC Dallas", "https://example.com/fcdallas.png", league),
                Team("Houston Dynamo FC", "https://example.com/houstondynamo.png", league),
                Team("Inter Miami CF", "https://example.com/intermiamicf.png", league),
                Team("LA Galaxy", "https://example.com/lalaxy.png", league),
                Team("LAFC", "https://example.com/lafc.png", league),
                Team("Minnesota United FC", "https://example.com/minnesotaunited.png", league),
                Team("Montreal Impact", "https://example.com/montrealimpact.png", league),
                Team("Nashville SC", "https://example.com/nashvillesc.png", league),
                Team("New England Revolution", "https://example.com/newenglandrevolution.png", league),
                Team("New York City FC", "https://example.com/newyorkcityfc.png", league),
                Team("New York Red Bulls", "https://example.com/newyorkredbulls.png", league),
                Team("Orlando City SC", "https://example.com/orlandocitysc.png", league),
                Team("Philadelphia Union", "https://example.com/philadelphiaunion.png", league),
                Team("Portland Timbers", "https://example.com/portlandtimbers.png", league),
                Team("Real Salt Lake", "https://example.com/realsaltlake.png", league),
                Team("San Jose Earthquakes", "https://example.com/sanjoseearthquakes.png", league),
                Team("Seattle Sounders FC", "https://example.com/seattlesounders.png", league),
                Team("Sporting Kansas City", "https://example.com/sportingkc.png", league),
                Team("St. Louis City SC", "https://example.com/stlouiscitysc.png", league),
                Team("Toronto FC", "https://example.com/torontofc.png", league),
                Team("Vancouver Whitecaps FC", "https://example.com/vancouverwhitecaps.png", league)
            )
            League.EPL -> listOf(
                Team("Arsenal", "https://example.com/arsenal.png", league),
                Team("Aston Villa", "https://example.com/astonvilla.png", league),
                Team("Bournemouth", "https://example.com/bournemouth.png", league),
                Team("Brentford", "https://example.com/brentford.png", league),
                Team("Brighton & Hove Albion", "https://example.com/brightonalbion.png", league),
                Team("Burnley", "https://example.com/burnley.png", league),
                Team("Chelsea", "https://example.com/chelsea.png", league),
                Team("Crystal Palace", "https://example.com/crystalpalace.png", league),
                Team("Everton", "https://example.com/everton.png", league),
                Team("Fulham", "https://example.com/fulham.png", league),
                Team("Liverpool", "https://example.com/liverpool.png", league),
                Team("Luton Town", "https://example.com/lutontown.png", league),
                Team("Manchester City", "https://example.com/mancity.png", league),
                Team("Manchester United", "https://example.com/manchesterunited.png", league),
                Team("Newcastle United", "https://example.com/newcastleunited.png", league),
                Team("Nottingham Forest", "https://example.com/nottinghamforest.png", league),
                Team("Sheffield United", "https://example.com/sheffieldunited.png", league),
                Team("Tottenham Hotspur", "https://example.com/tottenhamhotspur.png", league),
                Team("West Ham United", "https://example.com/westhamunited.png", league),
                Team("Wolverhampton Wanderers", "https://example.com/wolverhamptonwanderers.png", league)
            )
            League.BUNDESLIGA -> listOf(
                Team("FC Bayern Munich", "https://example.com/fcbayern.png", league),
                Team("Borussia Dortmund", "https://example.com/borussiadortmund.png", league),
                Team("RB Leipzig", "https://example.com/rbleipzig.png", league),
                Team("Bayer Leverkusen", "https://example.com/bayerleverkusen.png", league),
                Team("SC Freiburg", "https://example.com/scfreiburg.png", league),
                Team("Eintracht Frankfurt", "https://example.com/eintrachtfrankfurt.png", league),
                Team("VfL Wolfsburg", "https://example.com/vflwolfsburg.png", league),
                Team("Borussia Mönchengladbach", "https://example.com/borussiamonchengladbach.png", league),
                Team("1. FC Union Berlin", "https://example.com/1fcunionberlin.png", league),
                Team("1. FSV Mainz 05", "https://example.com/1fsvmainz05.png", league),
                Team("TSG Hoffenheim", "https://example.com/tsghoffenheim.png", league),
                Team("VfB Stuttgart", "https://example.com/vfbstuttgart.png", league),
                Team("VfL Bochum", "https://example.com/vflbochum.png", league),
                Team("FC Augsburg", "https://example.com/fcaugsburg.png", league),
                Team("Werder Bremen", "https://example.com/werderbremen.png", league),
                Team("1. FC Köln", "https://example.com/1fckoln.png", league),
                Team("SV Darmstadt 98", "https://example.com/svdarmstadt98.png", league),
                Team("1. FC Heidenheim", "https://example.com/1fcheidenheim.png", league)
            )
            League.SERIE_A -> listOf(
                Team("AC Milan", "https://example.com/acmilan.png", league),
                Team("AS Roma", "https://example.com/asroma.png", league),
                Team("Atalanta", "https://example.com/atalanta.png", league),
                Team("Bologna", "https://example.com/bologna.png", league),
                Team("Cagliari", "https://example.com/cagliari.png", league),
                Team("Empoli", "https://example.com/empoli.png", league),
                Team("Fiorentina", "https://example.com/fiorentina.png", league),
                Team("Frosinone", "https://example.com/frosinone.png", league),
                Team("Genoa", "https://example.com/genoa.png", league),
                Team("Hellas Verona", "https://example.com/hellasverona.png", league),
                Team("Inter Milan", "https://example.com/intermilan.png", league),
                Team("Juventus", "https://example.com/juventus.png", league),
                Team("Lazio", "https://example.com/lazio.png", league),
                Team("Lecce", "https://example.com/lecce.png", league),
                Team("Monza", "https://example.com/monza.png", league),
                Team("Napoli", "https://example.com/napoli.png", league),
                Team("Salernitana", "https://example.com/salernitana.png", league),
                Team("Sassuolo", "https://example.com/sassuolo.png", league),
                Team("Torino", "https://example.com/torino.png", league),
                Team("Udinese", "https://example.com/udinese.png", league)
            )
            League.UFC -> listOf(
                Team("Flyweight Division", "https://example.com/flyweight.png", league),
                Team("Bantamweight Division", "https://example.com/bantamweight.png", league),
                Team("Featherweight Division", "https://example.com/featherweight.png", league),
                Team("Lightweight Division", "https://example.com/lightweight.png", league),
                Team("Welterweight Division", "https://example.com/welterweight.png", league),
                Team("Middleweight Division", "https://example.com/middleweight.png", league),
                Team("Light Heavyweight Division", "https://example.com/lightheavyweight.png", league),
                Team("Heavyweight Division", "https://example.com/heavyweight.png", league),
                Team("Women's Strawweight Division", "https://example.com/womensstrawweight.png", league),
                Team("Women's Flyweight Division", "https://example.com/womensflyweight.png", league),
                Team("Women's Bantamweight Division", "https://example.com/womensbantamweight.png", league),
                Team("Women's Featherweight Division", "https://example.com/womensfeatherweight.png", league)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 