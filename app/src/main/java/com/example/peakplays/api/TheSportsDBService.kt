package com.example.peakplays.api

import android.util.Log
import com.example.peakplays.api.ApiClient.theSportsDBApi
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
        "Atlanta Hawks" to "134880",
        "Boston Celtics" to "134881",
        "Brooklyn Nets" to "134882",
        "Charlotte Hornets" to "134883",
        "Chicago Bulls" to "134884",
        "Cleveland Cavaliers" to "134885",
        "Dallas Mavericks" to "134886",
        "Denver Nuggets" to "134887",
        "Detroit Pistons" to "134888",
        "Golden State Warriors" to "134889",
        "Houston Rockets" to "134890",
        "Indiana Pacers" to "134891",
        "Los Angeles Clippers" to "134892",
        "Los Angeles Lakers" to "134893",
        "Memphis Grizzlies" to "134894",
        "Miami Heat" to "134895",
        "Milwaukee Bucks" to "134896",
        "Minnesota Timberwolves" to "134897",
        "New Orleans Pelicans" to "134898",
        "New York Knicks" to "134899",
        "Oklahoma City Thunder" to "134900",
        "Orlando Magic" to "134901",
        "Philadelphia 76ers" to "134902",
        "Phoenix Suns" to "134903",
        "Portland Trail Blazers" to "134904",
        "Sacramento Kings" to "134905",
        "San Antonio Spurs" to "134906",
        "Toronto Raptors" to "134907",
        "Utah Jazz" to "134908",
        "Washington Wizards" to "134909"
    )

    private val mlbTeamIds = mapOf(
        "Arizona Diamondbacks" to "133614",
        "Atlanta Braves" to "133615",
        "Baltimore Orioles" to "133616",
        "Boston Red Sox" to "133617",
        "Chicago Cubs" to "133618",
        "Chicago White Sox" to "133619",
        "Cincinnati Reds" to "133620",
        "Cleveland Guardians" to "133621",
        "Colorado Rockies" to "133622",
        "Detroit Tigers" to "133623",
        "Houston Astros" to "133624",
        "Kansas City Royals" to "133625",
        "Los Angeles Angels" to "133626",
        "Los Angeles Dodgers" to "133627",
        "Miami Marlins" to "133628",
        "Milwaukee Brewers" to "133629",
        "Minnesota Twins" to "133630",
        "New York Mets" to "133631",
        "New York Yankees" to "133632",
        "Oakland Athletics" to "133633",
        "Philadelphia Phillies" to "133634",
        "Pittsburgh Pirates" to "133635",
        "San Diego Padres" to "133636",
        "San Francisco Giants" to "133637",
        "Seattle Mariners" to "133638",
        "St. Louis Cardinals" to "133639",
        "Tampa Bay Rays" to "133640",
        "Texas Rangers" to "133641",
        "Toronto Blue Jays" to "133642",
        "Washington Nationals" to "133643"
    )

    private val nhlTeamIds = mapOf(
        "Anaheim Ducks" to "134845",
        "Arizona Coyotes" to "134846",
        "Boston Bruins" to "134847",
        "Buffalo Sabres" to "134848",
        "Calgary Flames" to "134849",
        "Carolina Hurricanes" to "134850",
        "Chicago Blackhawks" to "134851",
        "Colorado Avalanche" to "134852",
        "Columbus Blue Jackets" to "134853",
        "Dallas Stars" to "134854",
        "Detroit Red Wings" to "134855",
        "Edmonton Oilers" to "134856",
        "Florida Panthers" to "134857",
        "Los Angeles Kings" to "134858",
        "Minnesota Wild" to "134859",
        "Montreal Canadiens" to "134860",
        "Nashville Predators" to "134861",
        "New Jersey Devils" to "134862",
        "New York Islanders" to "134863",
        "New York Rangers" to "134864",
        "Ottawa Senators" to "134865",
        "Philadelphia Flyers" to "134866",
        "Pittsburgh Penguins" to "134867",
        "San Jose Sharks" to "134868",
        "Seattle Kraken" to "134869",
        "St. Louis Blues" to "134870",
        "Tampa Bay Lightning" to "134871",
        "Toronto Maple Leafs" to "134872",
        "Vancouver Canucks" to "134873",
        "Vegas Golden Knights" to "134874",
        "Washington Capitals" to "134875",
        "Winnipeg Jets" to "134876"
    )

    // Your Soccer maps were already correct (LaLiga, EPL, Bundesliga, Serie A, MLS)

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

    suspend fun getTeamRoster(teamName: String, idd: String,league: String): List<Player> {
        return try {
            Log.d("TheSportsDBService", "Starting roster fetch for team: $teamName in league: $idd")
            val teamId = idd //when (league) {
//                "NFL" -> nflTeamIds[teamName]
//                "NBA" -> nbaTeamIds[teamName]
//                "MLB" -> mlbTeamIds[teamName]
//                "NHL" -> nhlTeamIds[teamName]
//                "LaLiga" -> laligaTeamIds[teamName]
//                "Bundesliga" -> bundesligaTeamIds[teamName]
//                "EPL" -> premierLeagueTeamIds[teamName]
//                "Serie A" -> serieATeamIds[teamName]
//                "MLS" -> mlsTeamIds[teamName]
//                else -> null
//            } ?: run {
//                Log.w("TheSportsDBService", "Team name not found in mapping: $teamName for league: $league")
//                return emptyList()
            //}

            Log.d("TheSportsDBService", "Fetching team details for ID: $teamId")
            val teamResponse = theSportsDBApi.getTeams(league)
            val team = teamResponse.list?.firstOrNull()

            if (team == null) {
                Log.w("TheSportsDBService", "Team not found for ID: $teamId")
                return emptyList()
            }

            val playersResponse = theSportsDBApi.getTeamPlayers(teamId)
            val players = playersResponse.list ?: emptyList()

            players.mapNotNull { player ->
                try {
                    Player(
                        id = player.idPlayer ?: return@mapNotNull null,
                        name = player.strPlayer ?: return@mapNotNull null,
                        position = player.strPosition ?: "Unknown",
                        number = player.strNumber ?: "N/A",  // still safe default
                        teamId = teamId,
                        age = null, // you don't have age in the API, still null
                        weight = player.strWeight ?: "N/A",  // optional, may still be missing
                        height = player.strHeight ?: "N/A",  // optional, may still be missing
                        nationality = player.strNationality, // nullable field
                        dateOfBirth = player.dateBorn,        // nullable field
                        teamName = teamName,
                        league = league,
                        thumbUrl = player.strThumb,          // ✅ new field
                        cutoutUrl = player.strCutout,         // ✅ new field
                        renderUrl = player.strRender          // ✅ new field
                    )
                } catch (e: Exception) {
                    Log.e("TheSportsDBService", "Error converting player", e)
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("TheSportsDBService", "Error fetching roster", e)
            emptyList()
        }
    }
}
