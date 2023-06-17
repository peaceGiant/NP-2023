package Zadachi.Zad76;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here
class FootballTable {

    Map<String, List<FootballMatch>> mapByTeam;
    Map<String, Team> teams;

    public FootballTable() {
        mapByTeam = new HashMap<>();
        teams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam, homeGoals, awayGoals);
        mapByTeam.putIfAbsent(homeTeam, new ArrayList<>());
        mapByTeam.putIfAbsent(awayTeam, new ArrayList<>());
        mapByTeam.get(homeTeam).add(footballMatch);
        mapByTeam.get(awayTeam).add(footballMatch);

        teams.putIfAbsent(homeTeam, new Team(homeTeam));
        teams.putIfAbsent(awayTeam, new Team(awayTeam));
        teams.get(homeTeam).matches.add(footballMatch);
        teams.get(awayTeam).matches.add(footballMatch);

        teams.get(homeTeam).goalsW += homeGoals;
        teams.get(homeTeam).goalsL += awayGoals;
        teams.get(awayTeam).goalsL += homeGoals;
        teams.get(awayTeam).goalsW += awayGoals;
    }

    public void printTable() {
        Comparator<Team> comparator = (o1, o2) -> {
            if (o1.calculateTotal() != o2.calculateTotal())
                return Integer.compare(o2.calculateTotal(), o1.calculateTotal());
            if (o1.getGoalsWminusGoalsL() != o2.getGoalsWminusGoalsL())
                return Integer.compare(o2.getGoalsWminusGoalsL(), o1.getGoalsWminusGoalsL());
            return o1.name.compareTo(o2.name);
        };
        List<Team> result = teams.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        IntStream.range(0, result.size()).forEach(i -> {
            Team team = result.get(i);
            System.out.printf("%2d. %-15s%5d%5d%5d%5d%5d\n",
                    i+1, team.name, team.numberGames(), team.numberWins(), team.numberDraws(),
                    team.numberLoss(), team.calculateTotal());
        });
    }

}

class Team {

    String name;
    int goalsW;
    int goalsL;
    int wins;
    int draws;
    int losses;
    List<FootballMatch> matches;

    public String getName() {
        return name;
    }

    public int getGoalsWminusGoalsL() {
        return goalsW - goalsL;
    }

    public int getGoalsL() {
        return goalsL;
    }

    public Team(String name) {
        this.name = name;
        matches = new ArrayList<>();
    }

    int numberWins() {
        return (int) matches.stream().filter(match -> match.homeTeam.equals(name) || match.awayTeam.equals(name))
                .filter(match -> match.receivingPointsForTeam(name) == 3)
                .count();
    }

    int numberDraws() {
        return (int) matches.stream().filter(match -> match.homeTeam.equals(name) || match.awayTeam.equals(name))
                .filter(match -> match.receivingPointsForTeam(name) == 1)
                .count();
    }

    int numberLoss() {
        return (int) matches.stream().filter(match -> match.homeTeam.equals(name) || match.awayTeam.equals(name))
                .filter(match -> match.receivingPointsForTeam(name) == 0)
                .count();
    }

    int numberGames() {
        return numberWins() + numberLoss() + numberDraws();
    }

    int calculateTotal() {
        return numberWins() * 3 + numberDraws();
    }

}

class FootballMatch {

    String homeTeam;
    String awayTeam;
    int homeGoals;
    int awayGoals;

    public FootballMatch(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    int receivingPointsForTeam(String teamName) {
        if (teamName.equals(homeTeam)) {
            if (homeGoals > awayGoals) return 3;
            if (homeGoals < awayGoals) return 0;
        }
        else {
            if (awayGoals > homeGoals) return 3;
            if (awayGoals < homeGoals) return 0;
        }
        return 1;
    }

}
