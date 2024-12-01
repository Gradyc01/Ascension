package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;

public class TeamStats {
    private final Ascension plugin;
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;

    public TeamStats(Ascension plugin, Team team) {
        this.plugin = plugin;
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
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
        gameScore += num;
    }
}
