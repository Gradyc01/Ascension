package me.depickcator.ascension.Timeline.Events.Vaporization;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;

public class VaporizationChecks {
    private final Ascension plugin;
    private int threshold;
    private int enforcementTime;
    private final Timeline timeline;
    public VaporizationChecks(Timeline timeline) {
        this.timeline = timeline;
        plugin = Ascension.getInstance();
        threshold = 0;
    }

    /*Announces the new threshold to every team playing*/
    public void announceNewThreshold(int minutesBeforeEnforcement) {
        enforcementTime = timeline.getTimePassed() + minutesBeforeEnforcement;
        List<Team> teamList = TeamUtil.getEveryTeam(true);
        threshold = calculateNewThreshold(teamList);
        TextUtil.debugText("Vaporization Threshold set at " + threshold);
        for (Team team : teamList) {
            if (team.getTeamStats().getGameScore() < threshold) {
                sendWarningAnnouncement(team);
                team.updateTeamScoreboards();
            }
        }

    }

    private void enforceThreshold() {
        List<Team> teamList = TeamUtil.getEveryTeam(true);
        for (Team team : teamList) {
            if (!isAboveThreshold(team)) {
                for (Player p : team.getTeamMembers()) {
                    PlayerData pD = PlayerUtil.getPlayerData(p);
                    PlayerDeath.getInstance().setPlayerVaporized(pD);
                }
            }
        }
    }

    /*Returns the percentage that Team: team's progress has made towards the threshold
    * Returns -1 if Team: team has already reached the threshold */
    public int getPercentage(Team team) {
//        TextUtil.debugText("Threshold: " + threshold);
//        TextUtil.debugText("GameScore: " + team.getTeamStats().getGameScore());
        double percentage = threshold == 0 ? 1 : (float) team.getTeamStats().getGameScore() / (float) threshold;
//        TextUtil.debugText("Percentage: " + percentage);
        if (percentage >= 1) {
            return -1;
        } else {
            return (int) (percentage * 100);
        }
    }

    public boolean isAboveThreshold(Team team) {
        return team.getTeamStats().getGameScore() >= threshold;
    }

    public void reset() {
        threshold = 0;
    }

    private void sendWarningAnnouncement(Team team) {
        Title title = TextUtil.makeTitle(
                TextUtil.makeText("WARNING", TextUtil.DARK_RED),
                TextUtil.makeText("BELOW ENLIGHTENMENT THRESHOLD", TextUtil.RED),
                2, 5, 2);
        Component message = TextUtil.topBorder(TextUtil.RED)
                .append(TextUtil.makeText("Your team is currently below the enlightenment threshold. " +
                        "\n Become more enlightened to overcome this new challenge" +
                        "\n   Your team will be vaporized soon if you remain below. ", TextUtil.AQUA))
                .append(TextUtil.bottomBorder(TextUtil.RED));
        Audience audience = Audience.audience(team.getTeamMembers());
        SoundUtil.broadcastSound(Sound.ENTITY_WARDEN_EMERGE, 10, 2, team.getTeamMembers());
        audience.sendMessage(message);
        audience.showTitle(title);
    }

    public void sendWarningMessage() {
        if (timeline.getTimePassed() == enforcementTime) enforceThreshold();
        else {
            Component message = TextUtil.makeText("WARNING: ", TextUtil.DARK_RED)
                    .append(TextUtil.makeText("Your team is currently below the enlightenment threshold. You will be vaporized very soon.",
                            TextUtil.RED));
            Component actionBar = TextUtil.makeText("WARNING: ", TextUtil.DARK_RED)
                    .append(TextUtil.makeText("BELOW ENLIGHTENMENT THRESHOLD", TextUtil.RED));
            for (Team team : TeamUtil.getEveryTeam(true)) {
                if (team.getTeamStats().getGameScore() < threshold) {
                    TextUtil.broadcastMessage(message, team.getTeamMembers());
                    TextUtil.sendActionBar(team.getTeamMembers(), actionBar, 5 * 20);
                }
            }
        }
    }

    /*Calculates and returns the new threshold of game score
    * Also sorts the teamList*/
    private int calculateNewThreshold(List<Team> teamList) {
        teamList.sort(Comparator.comparing(team -> team.getTeamStats().getGameScore()));
        if (teamList.size() % 2 == 1) {
            return teamList.get(teamList.size() / 2).getTeamStats().getGameScore();
        } else {
            int scoreA = teamList.get(teamList.size() / 2).getTeamStats().getGameScore();
            int scoreB = teamList.get((teamList.size() / 2) - 1).getTeamStats().getGameScore();
            return (scoreA + scoreB) / 2;
        }
    }

    public int getThreshold() {
        return threshold;
    }

    public int getTimeTillEnforcement() {
        return enforcementTime - timeline.getTimePassed();
    }
}
