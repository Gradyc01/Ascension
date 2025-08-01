package me.depickcator.ascension.Teams;


import com.lunarclient.apollo.module.team.TeamMember;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamStats {
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;
    private int finalAscensionTimer;
    private boolean win;

    private int gameScoreRequirement;

    //Scavenger
    private List<Boolean> scavengerScore;

    public TeamStats(Team team) {
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
        win = false;
        scavengerScore = new ArrayList<>(List.of(
                false, false, false, false, false
        ));
        updateGameScoreRequirement();
    }

    //Lines Obtained
    public int getLinesObtained() {
        return linesObtained;
    }

    public void setLinesObtained(int linesObtained) {
        this.linesObtained = linesObtained;
    }

    //Items Obtained
    public int getItemsObtained() {
        return itemsObtained;
    }

    public void addItemObtained() {
        this.itemsObtained++;
    }

    //Game Score
    public void updateGameScoreRequirement() {
        gameScoreRequirement = Ascension.getInstance().getSettingsUI().getSettings().getAscensionGameScoreRequirement();
    }

    public int getGameScore() {
        return gameScore;
    }

    public void addGameScore(int num) {
        TextUtil.debugText("Added " + num + " game score to " + team.getLeader().getName() + "'s Team");
        TextUtil.debugText(team.getLeader().getName() + "'s Team previous score : " + gameScore);
//        int gameScoreThreshold = Ascension.getInstance().getSettingsUI().getSettings().getTimeline().getGameScoreThreshold();
        gameScore += num;
        if (!team.canRespawn()) {
//            gameScore = gameScoreThreshold;
            team.showNoRespawnLeftsMessage();
        }
        if (num > 0) gameScoreChangeText("You feel a little more enlightened", Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0);
        if (num < 0) gameScoreChangeText("You feel drained from your enlightenment", Sound.BLOCK_BEACON_DEACTIVATE, 1);

        team.attemptToRespawnTeamMembers();
        team.updateTeamScoreboards();

        TextUtil.debugText(team.getLeader().getName() + "'s Team current score : " + gameScore);
    }

    private void gameScoreChangeText(String text, Sound sound, float pitch) {
        TextUtil.broadcastMessage(TextUtil.makeText(text, TextUtil.GRAY), team.getTeamMembers());
        SoundUtil.broadcastSound(sound, 100, pitch, team.getTeamMembers());
    }

    public int getGameScorePercentage() {
        // TextUtil.debugText(gameScore + " Game Score     ");
        int gameScoreThreshold = Ascension.getInstance().getSettingsUI().getSettings().getTimeline().getGameScoreThreshold();
        int score = gameScore - gameScoreThreshold;
        int req = gameScoreRequirement - gameScoreThreshold;
        int round2Requirement = (int) (0.67 * req) ;
        double percentage;
        int ascensionAttempts = team.getTeamAscension().getAscensionAttempts();
        if (ascensionAttempts == 0) {
            percentage = (double) score / req;
        } else {
            int attemptsRemaining = ascensionAttempts - 1;
            percentage = (double) (score - req - (round2Requirement * attemptsRemaining)) / round2Requirement;
        }
        // TextUtil.debugText(Math.round(percentage * 100) + "%");
        return (int) Math.round(percentage * 100);

    }

    public boolean canBeginAscension() {
        return getGameScorePercentage() >= 100;
    }

    //Scavenger Score
    public List<Boolean> getScavengerScore() {
        return scavengerScore;
    }

    //Final Ascension Timer
    public int getFinalAscensionTimer() {
        return finalAscensionTimer;
    }

    public void addFinalAscensionTimer(int finalAscensionTimer) {
        setFinalAscensionTimer(getFinalAscensionTimer() + finalAscensionTimer);
    }

    public void setFinalAscensionTimer(int finalAscensionTimer) {
        this.finalAscensionTimer = finalAscensionTimer;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin() {
        this.win = true;
    }
}
