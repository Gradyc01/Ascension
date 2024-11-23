package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;

public class TeamStats {
    private final AscensionBingo plugin;
    private final Team team;

    //Statistics
    private int itemsObtained;
    private int linesObtained;

    public TeamStats(AscensionBingo plugin, Team team) {
        this.plugin = plugin;
        this.team = team;
        itemsObtained = 0;
        linesObtained = 0;
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

//    public void setItemsObtained(int itemsObtained) {
//        this.itemsObtained = itemsObtained;
//    }

    public void addItemObtained() {
        this.itemsObtained++;
    }
//    public void addLinesObtained(int lines) {
//        this.linesObtained+=lines;
//    }
}
