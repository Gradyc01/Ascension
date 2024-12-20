package me.depickcator.ascension.Teams;


import me.depickcator.ascension.General.TextUtil;

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

    //Scavenger
    private ArrayList<Boolean> scavengerScore;

    public TeamStats(Team team) {
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
        ascensionTimer = 300;
        scavengerScore = new ArrayList<>(List.of(
                false, false, false, false, false
        ));
    }

    public int getLinesObtained() {
        return linesObtained;
    }

    public void setLinesObtained(int linesObtained) {
        this.linesObtained = linesObtained;
    }

    public int getItemsObtained() {
        return itemsObtained;
    }

    public void addItemObtained() {
        this.itemsObtained++;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void addGameScore(int num) {
        TextUtil.debugText("Added " + num + " game score to " + team.getLeader().getName() + "'s Team");
        TextUtil.debugText(team.getLeader().getName() + "'s Team previous score : " + gameScore);
        gameScore += num;
        TextUtil.debugText(team.getLeader().getName() + "'s Team current score : " + gameScore);
    }

    public ArrayList<Boolean> getScavengerScore() {
        return scavengerScore;
    }

    public int getFinalAscensionTimer() {
        return finalAscensionTimer;
    }

    public void addFinalAscensionTimer(int finalAscensionTimer) {
        setFinalAscensionTimer(getFinalAscensionTimer() + finalAscensionTimer);
    }

    public int getAscensionTimer() {
        return ascensionTimer;
    }

    public void addAscensionTimer(int ascensionTimer) {
        this.ascensionTimer += ascensionTimer;
    }

    public void setFinalAscensionTimer(int finalAscensionTimer) {
        this.finalAscensionTimer = finalAscensionTimer;
    }
}
