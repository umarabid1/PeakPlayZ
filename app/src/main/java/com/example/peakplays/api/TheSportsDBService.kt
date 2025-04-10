package com.example.peakplays.api

import android.util.Log
import com.example.peakplays.models.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheSportsDBService @Inject constructor(private val api: TheSportsDBApi) {
    private val nflTeamIds = mapOf(
        "Arizona Cardinals" to "134946",
        "Atlanta Falcons" to "134947",
        "Baltimore Ravens" to "134948",
        "Buffalo Bills" to "134949",
        "Carolina Panthers" to "134940",
        "Chicago Bears" to "134941",
        "Cincinnati Bengals" to "134942",
        "Cleveland Browns" to "134943",
        "Dallas Cowboys" to "134944",
        "Denver Broncos" to "134945",
        "Detroit Lions" to "134934",
        "Green Bay Packers" to "134935",
        "Houston Texans" to "134936",
        "Indianapolis Colts" to "134937",
        "Jacksonville Jaguars" to "134938",
        "Kansas City Chiefs" to "134939",
        "Las Vegas Raiders" to "134928",
        "Los Angeles Chargers" to "134929",
        "Los Angeles Rams" to "134930",
        "Miami Dolphins" to "134931",
        "Minnesota Vikings" to "134932",
        "New England Patriots" to "134933",
        "New Orleans Saints" to "134922",
        "New York Giants" to "134923",
        "New York Jets" to "134924",
        "Philadelphia Eagles" to "134925",
        "Pittsburgh Steelers" to "134926",
        "San Francisco 49ers" to "134927",
        "Seattle Seahawks" to "134916",
        "Tampa Bay Buccaneers" to "134917",
        "Tennessee Titans" to "134918",
        "Washington Commanders" to "134919"
    )

    private val nbaTeamIds = mapOf(
        "134880" to "134880", // Atlanta Hawks
        "134881" to "134881", // Boston Celtics
        "134882" to "134882", // Brooklyn Nets
        "134883" to "134883", // Charlotte Hornets
        "134884" to "134884", // Chicago Bulls
        "134885" to "134885", // Cleveland Cavaliers
        "134886" to "134886", // Dallas Mavericks
        "134887" to "134887", // Denver Nuggets
        "134888" to "134888", // Detroit Pistons
        "134889" to "134889", // Golden State Warriors
        "134890" to "134890", // Houston Rockets
        "134891" to "134891", // Indiana Pacers
        "134892" to "134892", // Los Angeles Clippers
        "134893" to "134893", // Los Angeles Lakers
        "134894" to "134894", // Memphis Grizzlies
        "134895" to "134895", // Miami Heat
        "134896" to "134896", // Milwaukee Bucks
        "134897" to "134897", // Minnesota Timberwolves
        "134898" to "134898", // New Orleans Pelicans
        "134899" to "134899", // New York Knicks
        "134900" to "134900", // Oklahoma City Thunder
        "134901" to "134901", // Orlando Magic
        "134902" to "134902", // Philadelphia 76ers
        "134903" to "134903", // Phoenix Suns
        "134904" to "134904", // Portland Trail Blazers
        "134905" to "134905", // Sacramento Kings
        "134906" to "134906", // San Antonio Spurs
        "134907" to "134907", // Toronto Raptors
        "134908" to "134908", // Utah Jazz
        "134909" to "134909"  // Washington Wizards
    )

    private val mlbTeamIds = mapOf(
        "133614" to "133614", // Arizona Diamondbacks
        "133615" to "133615", // Atlanta Braves
        "133616" to "133616", // Baltimore Orioles
        "133617" to "133617", // Boston Red Sox
        "133618" to "133618", // Chicago Cubs
        "133619" to "133619", // Chicago White Sox
        "133620" to "133620", // Cincinnati Reds
        "133621" to "133621", // Cleveland Guardians
        "133622" to "133622", // Colorado Rockies
        "133623" to "133623", // Detroit Tigers
        "133624" to "133624", // Houston Astros
        "133625" to "133625", // Kansas City Royals
        "133626" to "133626", // Los Angeles Angels
        "133627" to "133627", // Los Angeles Dodgers
        "133628" to "133628", // Miami Marlins
        "133629" to "133629", // Milwaukee Brewers
        "133630" to "133630", // Minnesota Twins
        "133631" to "133631", // New York Mets
        "133632" to "133632", // New York Yankees
        "133633" to "133633", // Oakland Athletics
        "133634" to "133634", // Philadelphia Phillies
        "133635" to "133635", // Pittsburgh Pirates
        "133636" to "133636", // San Diego Padres
        "133637" to "133637", // San Francisco Giants
        "133638" to "133638", // Seattle Mariners
        "133639" to "133639", // St. Louis Cardinals
        "133640" to "133640", // Tampa Bay Rays
        "133641" to "133641", // Texas Rangers
        "133642" to "133642", // Toronto Blue Jays
        "133643" to "133643"  // Washington Nationals
    )

    private val nhlTeamIds = mapOf(
        "134845" to "134845", // Anaheim Ducks
        "134846" to "134846", // Arizona Coyotes
        "134847" to "134847", // Boston Bruins
        "134848" to "134848", // Buffalo Sabres
        "134849" to "134849", // Calgary Flames
        "134850" to "134850", // Carolina Hurricanes
        "134851" to "134851", // Chicago Blackhawks
        "134852" to "134852", // Colorado Avalanche
        "134853" to "134853", // Columbus Blue Jackets
        "134854" to "134854", // Dallas Stars
        "134855" to "134855", // Detroit Red Wings
        "134856" to "134856", // Edmonton Oilers
        "134857" to "134857", // Florida Panthers
        "134858" to "134858", // Los Angeles Kings
        "134859" to "134859", // Minnesota Wild
        "134860" to "134860", // Montreal Canadiens
        "134861" to "134861", // Nashville Predators
        "134862" to "134862", // New Jersey Devils
        "134863" to "134863", // New York Islanders
        "134864" to "134864", // New York Rangers
        "134865" to "134865", // Ottawa Senators
        "134866" to "134866", // Philadelphia Flyers
        "134867" to "134867", // Pittsburgh Penguins
        "134868" to "134868", // San Jose Sharks
        "134869" to "134869", // Seattle Kraken
        "134870" to "134870", // St. Louis Blues
        "134871" to "134871", // Tampa Bay Lightning
        "134872" to "134872", // Toronto Maple Leafs
        "134873" to "134873", // Vancouver Canucks
        "134874" to "134874", // Vegas Golden Knights
        "134875" to "134875", // Washington Capitals
        "134876" to "134876"  // Winnipeg Jets
    )

    // Soccer League Team IDs
    private val laligaTeamIds = mapOf(
        "Real Madrid" to "133613",
        "Barcelona" to "133614",
        "Atletico Madrid" to "133615",
        "Sevilla" to "133616",
        "Real Sociedad" to "133617",
        "Real Betis" to "133618",
        "Villarreal" to "133619",
        "Athletic Bilbao" to "133620",
        "Valencia" to "133621",
        "Celta Vigo" to "133622",
        "Getafe" to "133623",
        "Girona" to "133624",
        "Osasuna" to "133625",
        "Mallorca" to "133626",
        "Rayo Vallecano" to "133627",
        "Alaves" to "133628",
        "Cadiz" to "133629",
        "Granada" to "133630",
        "Las Palmas" to "133631",
        "Almeria" to "133632"
    )

    private val bundesligaTeamIds = mapOf(
        "Bayern Munich" to "133633",
        "Borussia Dortmund" to "133634",
        "RB Leipzig" to "133635",
        "Bayer Leverkusen" to "133636",
        "Eintracht Frankfurt" to "133637",
        "Wolfsburg" to "133638",
        "Hoffenheim" to "133639",
        "Freiburg" to "133640",
        "Mainz" to "133641",
        "Union Berlin" to "133642",
        "Borussia Monchengladbach" to "133643",
        "Werder Bremen" to "133644",
        "Augsburg" to "133645",
        "Bochum" to "133646",
        "Stuttgart" to "133647",
        "Heidenheim" to "133648",
        "Darmstadt" to "133649",
        "Koln" to "133650"
    )

    private val premierLeagueTeamIds = mapOf(
        "Arsenal" to "133651",
        "Manchester City" to "133652",
        "Manchester United" to "133653",
        "Liverpool" to "133654",
        "Chelsea" to "133655",
        "Tottenham" to "133656",
        "Newcastle" to "133657",
        "Aston Villa" to "133658",
        "Brighton" to "133659",
        "West Ham" to "133660",
        "Brentford" to "133661",
        "Crystal Palace" to "133662",
        "Wolves" to "133663",
        "Fulham" to "133664",
        "Nottingham Forest" to "133665",
        "Everton" to "133666",
        "Burnley" to "133667",
        "Luton Town" to "133668",
        "Sheffield United" to "133669",
        "Bournemouth" to "133670"
    )

    private val serieATeamIds = mapOf(
        "Inter Milan" to "133671",
        "AC Milan" to "133672",
        "Juventus" to "133673",
        "Napoli" to "133674",
        "Roma" to "133675",
        "Lazio" to "133676",
        "Atalanta" to "133677",
        "Fiorentina" to "133678",
        "Bologna" to "133679",
        "Torino" to "133680",
        "Monza" to "133681",
        "Genoa" to "133682",
        "Lecce" to "133683",
        "Sassuolo" to "133684",
        "Frosinone" to "133685",
        "Udinese" to "133686",
        "Cagliari" to "133687",
        "Verona" to "133688",
        "Empoli" to "133689",
        "Salernitana" to "133690"
    )

    private val mlsTeamIds = mapOf(
        "Atlanta United" to "133691",
        "Austin FC" to "133692",
        "Charlotte FC" to "133693",
        "Chicago Fire" to "133694",
        "FC Cincinnati" to "133695",
        "Colorado Rapids" to "133696",
        "Columbus Crew" to "133697",
        "DC United" to "133698",
        "FC Dallas" to "133699",
        "Houston Dynamo" to "133700",
        "Inter Miami" to "133701",
        "LA Galaxy" to "133702",
        "Los Angeles FC" to "133703",
        "Minnesota United" to "133704",
        "CF Montreal" to "133705",
        "Nashville SC" to "133706",
        "New England Revolution" to "133707",
        "New York City FC" to "133708",
        "New York Red Bulls" to "133709",
        "Orlando City" to "133710",
        "Philadelphia Union" to "133711",
        "Portland Timbers" to "133712",
        "Real Salt Lake" to "133713",
        "San Jose Earthquakes" to "133714",
        "Seattle Sounders" to "133715",
        "Sporting Kansas City" to "133716",
        "St. Louis City SC" to "133717",
        "Toronto FC" to "133718",
        "Vancouver Whitecaps" to "133719"
    )

    suspend fun getTeamRoster(teamName: String, league: String): List<Player> {
        return try {
            Log.d("TheSportsDBService", "Starting roster fetch for team: $teamName in league: $league")
            
            // Get the team ID from the appropriate mapping
            val teamId = when (league) {
                "NFL" -> nflTeamIds[teamName]
                "NBA" -> nbaTeamIds[teamName]
                "MLB" -> mlbTeamIds[teamName]
                "NHL" -> nhlTeamIds[teamName]
                "LaLiga" -> laligaTeamIds[teamName]
                "Bundesliga" -> bundesligaTeamIds[teamName]
                "Premier League" -> premierLeagueTeamIds[teamName]
                "Serie A" -> serieATeamIds[teamName]
                "MLS" -> mlsTeamIds[teamName]
                else -> null
            } ?: run {
                Log.w("TheSportsDBService", "Team name not found in mapping: $teamName for league: $league")
                return emptyList()
            }
            
            Log.d("TheSportsDBService", "Fetching team details for ID: $teamId")
            
            // First verify the team exists and get its details
            val teamResponse = api.getTeamDetails(teamId)
            val team = teamResponse.teams?.firstOrNull()
            
            if (team == null) {
                Log.w("TheSportsDBService", "Team not found for ID: $teamId")
                return emptyList()
            }
            
            Log.d("TheSportsDBService", "Found team: ${team.strTeam}, League: ${team.strLeague}")
            
            // Now get the players for this team
            Log.d("TheSportsDBService", "Fetching players for team ID: $teamId")
            val playersResponse = api.getTeamPlayers(teamId)
            val players = playersResponse.player ?: emptyList()
            
            Log.d("TheSportsDBService", "Found ${players.size} players for team: $teamName")
            
            // Convert to our Player model
            val convertedPlayers = players.mapNotNull { player ->
                try {
                    Player(
                        id = player.idPlayer ?: return@mapNotNull null,
                        name = player.strPlayer ?: return@mapNotNull null,
                        position = player.strPosition ?: "Unknown",
                        number = player.strNumber ?: "N/A",
                        teamId = teamId,
                        age = null,
                        weight = player.strWeight ?: "N/A",
                        height = player.strHeight ?: "N/A",
                        nationality = player.strNationality,
                        dateOfBirth = player.dateBorn,
                        teamName = teamName,
                        league = league
                    )
                } catch (e: Exception) {
                    Log.e("TheSportsDBService", "Error converting player: ${player.strPlayer}", e)
                    null
                }
            }

            Log.d("TheSportsDBService", "Processed ${convertedPlayers.size} players for team: $teamName")
            convertedPlayers

        } catch (e: Exception) {
            Log.e("TheSportsDBService", "Error fetching roster for team: $teamName", e)
            emptyList()
        }
    }
} 