package org.bingo4.Teams;


import org.bingo4.Bingo4;
import org.bingo4.Timeline.Events.BingoLockdown.BingoLockdown;
import org.bingo4.Utility.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class TeamStats {
    private final Team team;
    private final Bingo4 plugin = Bingo4.getInstance();

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;
    private int finalAscensionTimer;
    private boolean win;
    private int gameScoreDifferential;

    private int teamLineRequirement;


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
        BingoLockdown bingoLockdown = plugin.getSettingsUI().getSettings().getTimeline().getBingoLockdown();
        if (bingoLockdown.canStartEvent(team)) {
            bingoLockdown.start(team);
        }
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
        teamLineRequirement = Bingo4.getInstance().getSettingsUI().getSettings().getBingoLinesRequirement();
    }

    public int getGameScore() {
        return gameScore;
    }

    public void addGameScore(int num) {
        TextUtil.debugText("Added " + num + " game score to " + team.getLeader().getName() + "'s Team");
        TextUtil.debugText(team.getLeader().getName() + "'s Team previous score : " + gameScore);
        gameScore += num;
        if (!team.canRespawn()) {
            team.showNoRespawnLeftsMessage();
        }
        team.attemptToRespawnTeamMembers();
        for (Team team : TeamUtil.getEveryTeam()) {
            team.getTeamStats().recalculateScoreDifferential();
        }

        TextUtil.debugText(team.getLeader().getName() + "'s Team current score : " + gameScore);
    }

    public void recalculateScoreDifferential() {
        int highestScore = 0;
        for (Team team : TeamUtil.getEveryTeam()) {
            if (team.equals(this.team)) continue;
            highestScore = Integer.max(team.getTeamStats().getGameScore(), highestScore);
        }
        gameScoreDifferential = getGameScore() - highestScore;
        this.team.updateTeamScoreboards();
        this.team.getTeamComeback().recalculateComebackAdvantages(gameScoreDifferential);
    }

    public boolean isBingoLockdown() {
        return this.linesObtained >= plugin.getSettingsUI().getSettings().getBingoLinesRequirement();
    }

    //Scavenger Score
    public List<Boolean> getScavengerScore() {
        return scavengerScore;
    }

    //Final Bingo4 Timer
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

    public int getGameScoreDifferential() {
        return gameScoreDifferential;
    }
}
