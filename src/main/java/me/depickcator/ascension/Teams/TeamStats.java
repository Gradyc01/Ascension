package me.depickcator.ascension.Teams;


import com.lunarclient.apollo.module.team.TeamMember;
import me.depickcator.ascension.Ascension;
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
//    private int ascensionTimer;
//    private int ascensionAttempts;
    private boolean win;

    private int gameScoreRequirement;
//    private final static int ascensionStartingTime = 900;

    //Scavenger
    private List<Boolean> scavengerScore;

    public TeamStats(Team team) {
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
//        ascensionAttempts = 0;
        win = false;
//        ascensionTimer = Ascension.getInstance().getSettingsUI().getSettings().getStartingAscensionTimer();
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

        gameScore += num;
        if (gameScore <= 0) {
            gameScore = 0;
            Title title = TextUtil.makeTitle(TextUtil.makeText("No more Respawns", TextUtil.RED),
                    TextUtil.makeText("Become more enlightened in order to respawn again", TextUtil.YELLOW),
                    1, 5, 1);
            Audience.audience(team.getTeamMembers()).showTitle(title);
        }
        if (num > 0) {
            gameScoreChangeText("You feel a little more enlightened", Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0);
        }
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
        int round2Requirement = 2 * gameScoreRequirement / 3;
        double percentage;
        int ascensionAttempts = team.getTeamAscension().getAscensionAttempts();
        if (ascensionAttempts == 0) {
            percentage = (double) gameScore / gameScoreRequirement;
        } else {
            int attemptsRemaining = ascensionAttempts - 1;
            percentage = (double) (gameScore - gameScoreRequirement - (round2Requirement * attemptsRemaining)) / round2Requirement;
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


//    //Ascension Timer
//    public int getAscensionTimer() {
//        return ascensionTimer;
//    }
//
//    public void addAscensionTimer(int ascensionTimer) {
//        this.ascensionTimer += ascensionTimer;
//    }
//
//    public void setAscensionTimer(int ascensionTimer) {
//        this.ascensionTimer = ascensionTimer;
//    }
//
//    //Ascension Attempts
//    public int getAscensionAttempts() {
//        return ascensionAttempts;
//    }
//
//    public void addAscensionAttempts() {
//        this.ascensionAttempts++;
//        team.updateTeamScoreboards();
//    }

    public boolean isWin() {
        return win;
    }

    public void setWin() {
        this.win = true;
    }

//    public void setAscensionAttempts(int ascensionAttempts) {
//        this.ascensionAttempts = ascensionAttempts;
//    }
}
