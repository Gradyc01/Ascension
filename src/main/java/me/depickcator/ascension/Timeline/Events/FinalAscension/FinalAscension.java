package me.depickcator.ascension.Timeline.Events.FinalAscension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;

public class FinalAscension {
    private final Ascension plugin;
    public FinalAscension() {
        this.plugin = Ascension.getInstance();

    }

    private void setGameState() throws Exception {
        GameStates gameState = plugin.getGameState();
        if (!gameState.inGame()) throw new Exception("Incorrect State");
        gameState.setCurrentState(GameStates.GAME_FINAL_ASCENSION);
    }
}
