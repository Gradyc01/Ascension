package me.depickcator.ascension.Timeline.Events.FinalAscension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Player.Data.Scoreboards.GameBoard;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Timeline.Events.Winner.Winner;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FinalAscension {
    private final Ascension plugin;
    public FinalAscension(int initialBorderShrinkSize) {
        this.plugin = Ascension.getInstance();
        TextUtil.debugText("Final Ascension Ran");
        PlayerDeath.getInstance().respawnEveryone();
        setGameState();
        calculateTeamTimers();
        finalAscensionStartAnnouncement();
        timer();
        plugin.getWorld().getWorldBorder().setSize(initialBorderShrinkSize, 300);
    }

    private void setGameState() {
        GameStates gameState = plugin.getGameState();
        gameState.setCurrentState(GameStates.GAME_FINAL_ASCENSION);
        TextUtil.debugText("Game States Ran");
    }

    private void calculateTeamTimers() {
        List<Team> allTeams = TeamUtil.getEveryTeam();
        for (Team t : allTeams) {
            TeamStats teamStats = t.getTeamStats();
            int time = calculateTimer(teamStats.getGameScore());
            teamStats.setFinalAscensionTimer(time);
            TextUtil.debugText("Team Score: " + teamStats.getFinalAscensionTimer());
        }
    }

    private void timer() {
        new BukkitRunnable() {
            int time = 0;
            List<Team> activeTeams = TeamUtil.getEveryTeam(true);
            @Override
            public void run() {
                if (!plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION)) {
                    cancel();
                    TextUtil.debugText("No Longer in the correct state");
                    return;
                }
                if (activeTeams.size() <= 1) {
                    cancel();
                    new Winner(activeTeams);
                    TextUtil.debugText("win");
                    return;
                }
                time++;
                if (time == 300) {
                    plugin.getWorld().getWorldBorder().setSize(50, 600);
                }
//                for (Team t : activeTeams) {
//                    updateScoreboard(t);
//                    if (t.checkSTATE(Team.STATE_DEPRECATED)) {
//                        activeTeams.remove(t);
//                        TextUtil.debugText("A TEAM LOST");
//                        teamEliminatedMessage(t);
//                        continue;
//                    }
//                    TeamStats teamStats = t.getTeamStats();
//                    if (teamStats.getFinalAscensionTimer() <= 0) {
//                        timerExpired(t);
//                        continue;
//                    }
//                    teamStats.addFinalAscensionTimer(-1);
//                }
                activeTeams = updateTeams(activeTeams);
            }

        }.runTaskTimer(plugin, 2 * 20, 20);
    }

    private List<Team> updateTeams(List<Team> teams) {
        List<Team> activeTeams = new ArrayList<>(teams);
        for (Team t : activeTeams) {
            updateScoreboard(t);
            TeamStats teamStats = t.getTeamStats();
            if (teamStats.getFinalAscensionTimer() <= 0) {
                timerExpired(t);
            }
            if (t.checkSTATE(Team.STATE_DEPRECATED)) {
                activeTeams.remove(t);
                TextUtil.debugText("team lost");
                teamEliminatedMessage(t);
                return updateTeams(activeTeams);
            }

            teamStats.addFinalAscensionTimer(-1);
        }
        return activeTeams;
    }

    //Effects after a teams timer expired
    private void timerExpired(Team team) {
        List<Player> players = team.getTeamMembers();
        for (Player p : players) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            PlayerDeath.getInstance().playerDied(pD);
        }
    }

    //Calculates the ascension timer
    private int calculateTimer(int gameScore) {
        int defaultTimer = 300;
        int multiplier = 30;
        return defaultTimer + gameScore * multiplier;
    }

    //updates the scoreboard
    private void updateScoreboard(Team team) {
        for (Player p : team.getTeamMembers()) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            GameBoard b = (GameBoard) pD.getPlayerScoreboard().getBoards();
            b.updateTime();
        }
    }

    private void teamEliminatedMessage(Team t) {
        Component lost = TextUtil.makeText("            YOU LOST   \n\n", TextUtil.RED, true, true);
//        Component description = TextUtil.makeText(
//                "      Your team has failed to ascend.      " +
//                        "\n      You collapsed at the final hurdle and couldn't get up. " +
//                        "\n      Maybe another day and another time.", TextUtil.AQUA);
        Component description = TextUtil.makeText(
                "      Your team has failed to ascend.      ",TextUtil.AQUA);
        Component finalText = TextUtil.topBorder(TextUtil.YELLOW).append(lost).append(description).append(TextUtil.bottomBorder(TextUtil.YELLOW));
        TextUtil.broadcastMessage(finalText, t.getTeamMembers());
    }

    private void finalAscensionStartAnnouncement() {
        Component titleText = TextUtil.makeText("FINAL ASCENSION", TextUtil.YELLOW);
        Component subtitleText = TextUtil.makeText("The border will now begin to shrink", TextUtil.YELLOW);
        Title title = TextUtil.makeTitle(titleText, subtitleText, 1, 3, 1);
        TextUtil.broadcastTitle(title);
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 1f, 1f);
    }

}

