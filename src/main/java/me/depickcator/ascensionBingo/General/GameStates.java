package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;

public class GameStates {
    public final static int UNLOADED = 0;
    public final static int LOBBY = 1;
    public final static int GAME = 2;
    public final static int GAME_LOADING = 3;
    public final static int GAME_ENDING = 4;

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
        return checkState(GAME) || checkState(GAME_LOADING) || checkState(GAME_ENDING);
    }
}
