package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;

public class GameStates {
    public final static int UNLOADED = 0;
    public final static int LOBBY = 1;
    public final static int GAME_BEFORE_GRACE = 2;
    public final static int GAME_LOADING = 3;

    public final static int GAME_ENDING = 4;
    public final static int GAME_AFTER_GRACE = 5;
    public final static int GAME_FINAL_ASCENSION = 6;

    private int currentState;

    private AscensionBingo plugin;

    public GameStates(AscensionBingo plugin) {
        currentState = UNLOADED;
        this.plugin = plugin;
    }

    //Checks If the current state matches the state given
    // returns true if it matches
    // returns false if it doesn't
    public boolean checkState(int state) {
        return state == currentState;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public boolean canNotBuild() {
        return currentState == LOBBY || checkState(GAME_LOADING);
    }

    public boolean inGame() {
        return checkState(GAME_BEFORE_GRACE) || checkState(GAME_LOADING) || checkState(GAME_ENDING) || checkState(GAME_AFTER_GRACE);
    }

    public boolean canNotPVP() {
        return checkState(GAME_BEFORE_GRACE) || checkState(GAME_ENDING) || checkState(GAME_LOADING);
    }
}
