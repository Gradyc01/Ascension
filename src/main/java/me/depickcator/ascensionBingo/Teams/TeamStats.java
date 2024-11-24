package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;

public class TeamStats {
    private final AscensionBingo plugin;
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;

    public TeamStats(AscensionBingo plugin, Team team) {
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
