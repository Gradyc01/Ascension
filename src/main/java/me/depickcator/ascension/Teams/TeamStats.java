package me.depickcator.ascension.Teams;


import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class TeamStats {
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;
    private int finalAscensionTimer;
    private int ascensionTimer;
    private int ascensionAttempts;

    //Scavenger
    private ArrayList<Boolean> scavengerScore;

    public TeamStats(Team team) {
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
        ascensionAttempts = 0;
        ascensionTimer = 300;
        scavengerScore = new ArrayList<>(List.of(
                false, false, false, false, false
        ));
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
    public int getGameScore() {
        return gameScore;
    }

    public void addGameScore(int num) {
        TextUtil.debugText("Added " + num + " game score to " + team.getLeader().getName() + "'s Team");
        TextUtil.debugText(team.getLeader().getName() + "'s Team previous score : " + gameScore);

        gameScore += num;
        if (num > 0) {
            TextUtil.broadcastMessage(TextUtil.makeText("You feel a little more enlightened", TextUtil.GRAY), team.getTeamMembers());
            SoundUtil.broadcastSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 100, 0, team.getTeamMembers());
        }
        team.updateTeamScoreboards();

        TextUtil.debugText(team.getLeader().getName() + "'s Team current score : " + gameScore);
    }

    public int getGameScorePercentage() {
        TextUtil.debugText(gameScore + " Game Score     ");
        double percentage;
        if (ascensionAttempts == 0) {
            percentage = (double) gameScore / 25;
        } else {
            int attemptsRemaining = ascensionAttempts - 1;
//            return (int) ((double)  (( gameScore - 25 - (15 * attemptsRemaining)) / 15))* 100;
            percentage = (double) (gameScore - 25 - (15 * attemptsRemaining)) / 15;
        }
        TextUtil.debugText(Math.round(percentage * 100) + "%");
        return (int) Math.round(percentage * 100);

    }

    public boolean canBeginAscension() {
        return getGameScorePercentage() >= 100;
    }

    //Scavenger Score
    public ArrayList<Boolean> getScavengerScore() {
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


    //Ascension Timer
    public int getAscensionTimer() {
        return ascensionTimer;
    }

    public void addAscensionTimer(int ascensionTimer) {
        this.ascensionTimer += ascensionTimer;
    }

    //Ascension Attempts
    public int getAscensionAttempts() {
        return ascensionAttempts;
    }

    public void addAscensionAttempts() {
        this.ascensionAttempts++;
        team.updateTeamScoreboards();
    }

//    public void setAscensionAttempts(int ascensionAttempts) {
//        this.ascensionAttempts = ascensionAttempts;
//    }
}
