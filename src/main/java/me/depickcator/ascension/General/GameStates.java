package me.depickcator.ascension.General;


import org.bukkit.entity.Player;

public class GameStates {
    public final static int UNLOADED = 0;
    public final static int LOBBY = 1;
    public final static int GAME_BEFORE_GRACE = 2;
    public final static int GAME_LOADING = 3;
    public final static int GAME_AFTER_GRACE = 4;
    public final static int GAME_FEAST_LOADING = 5;
    public final static int GAME_FINAL_ASCENSION = 6;
    public final static int GAME_ENDING = 7;

    public final static int GAME_ASCENSION = 8;

    private int currentState;

    public GameStates() {
        currentState = UNLOADED;
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
        return  checkState(LOBBY) ||
                checkState(GAME_LOADING) ||
                checkState(GAME_FEAST_LOADING);
    }

    public boolean canTeleport(Player p) {
//        return !(checkState(GAME_FEAST_LOADING) ||
//                checkState(GAME_FINAL_ASCENSION) ||
//                checkState(LOBBY) ||
//                checkState(GAME_ENDING));
        if (checkState(GAME_FINAL_ASCENSION) || checkState(GAME_AFTER_GRACE)) {
            return true;
        } else {
            TextUtil.errorMessage(p, "Teleporting is currently unavailable");
            return false;
        }
    }

    public boolean inGame() {
        return checkState(GAME_BEFORE_GRACE) ||
                checkState(GAME_LOADING) ||
                checkState(GAME_ENDING) ||
                checkState(GAME_AFTER_GRACE) ||
                checkState(GAME_FINAL_ASCENSION);
    }

    public boolean canNotPVP() {
        return checkState(GAME_BEFORE_GRACE) ||
                checkState(GAME_ENDING) ||
                checkState(GAME_LOADING) ||
                checkState(GAME_FEAST_LOADING);
    }
}
