package org.bingo4.Teams;

import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;

public class TeamBingoLockdown {
    private final Team team;
    private final Bingo4 plugin;
    private int ascensionTimer;
    public TeamBingoLockdown(Team team) {
        this.team = team;
        plugin = Bingo4.getInstance();
        ascensionTimer = plugin.getSettingsUI().getSettings().getStartingAscensionTimer();
    }

    /*Called when an Bingo4 is started for the Team: team*/
    public void startAscension() {
        TextUtil.debugText("Starting Ascension");
    }

    /*Called when an Bingo4 is failed for the Team: team*/
    public void failedAscension() {
        TextUtil.debugText("Failed Bingo4");
        setAscensionTimer(Integer.max((int) (getAscensionTimer() * 1.3), 60));
    }

    //Bingo4 Timer
    public int getAscensionTimer() {
        return ascensionTimer;
    }

    public void addAscensionTimer(int ascensionTimer) {
        this.ascensionTimer += ascensionTimer;
    }

    private void setAscensionTimer(int ascensionTimer) {
        this.ascensionTimer = ascensionTimer;
    }



}
