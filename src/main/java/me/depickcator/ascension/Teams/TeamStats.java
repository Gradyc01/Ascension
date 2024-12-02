package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class TeamStats {
    private final Ascension plugin;
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;
    private int gameScore;

    //Scavenger
    private ArrayList<Boolean> scavengerScore;

    public TeamStats(Ascension plugin, Team team) {
        this.plugin = plugin;
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
        gameScore = 0;
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
        gameScore += num;
    }

    public ArrayList<Boolean> getScavengerScore() {
        return scavengerScore;
    }

    public void setScavengerScore(ArrayList<Boolean> scavengerScore) {
        this.scavengerScore = scavengerScore;
    }
}
