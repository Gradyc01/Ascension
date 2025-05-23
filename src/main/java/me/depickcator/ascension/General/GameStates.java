package me.depickcator.ascension.General;


import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;

public class GameStates {
    public final static int UNLOADED = 0;
    public final static int LOBBY_NORMAL = 1;
    public final static int LOBBY_QUEUE = 2;
    public final static int GAME_BEFORE_GRACE = 3;
    public final static int GAME_LOADING = 4;
    public final static int GAME_AFTER_GRACE = 5;
    public final static int GAME_FEAST_LOADING = 6;
    public final static int GAME_FINAL_ASCENSION = 7;
    public final static int GAME_ENDING = 8;
    public final static int GAME_PAUSED = 10;

    public final static int GAME_ASCENSION = 9;

    private int currentState;
    private int previousState;

    public GameStates() {
        currentState = UNLOADED;
        previousState = UNLOADED;
    }

    /*Checks whether the game is currently in one of these states
    * Return True if it is, False Otherwise*/
    public boolean checkState(int... state) {
        for (int s : state) {
            if (s == currentState) return true;
        }
        return false;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getPreviousState() {
        return previousState;
    }

    public void setCurrentState(int currentState) {
        previousState = this.currentState;
        this.currentState = currentState;
        TextUtil.debugText("Current state: " + currentState + "             Previous State: " + previousState);

    }

    /*Checks if game is in a state where players can not build
    * Returns True if Players can't build
    * Returns False Otherwise*/
    public boolean canNotBuild() {
        return checkState(LOBBY_NORMAL, GAME_LOADING, GAME_FEAST_LOADING, GAME_PAUSED);
    }

    /*Checks if game is in a lobby state
     * Returns True if Yes
     * Returns False Otherwise*/
    public boolean inLobby() {
        return checkState(LOBBY_NORMAL, LOBBY_QUEUE);
    }

    /*Check if Game is in a state where players can teleport
     * Returns True if Yes
     * Returns False Otherwise*/
    public boolean canTeleport(Player p) {
        if (checkState(GAME_FINAL_ASCENSION, GAME_AFTER_GRACE, GAME_ASCENSION, GAME_PAUSED)) {
            return true;
        } else {
            TextUtil.errorMessage(p, "Teleporting is currently unavailable");
            return false;
        }
    }

    /*Check if Game is in Game state
     * Returns True if Yes
     * Returns False Otherwise*/
    public boolean inGame() {
        return checkState(GAME_BEFORE_GRACE, GAME_LOADING, GAME_AFTER_GRACE, GAME_ASCENSION, GAME_FINAL_ASCENSION, GAME_ENDING, GAME_PAUSED);
    }

    /*Check if Game is in a state where players can pvp
     * Returns True if Yes
     * Returns False Otherwise*/
    public boolean canNotPVP() {
        return checkState(GAME_BEFORE_GRACE, GAME_LOADING, GAME_ENDING, GAME_FEAST_LOADING, GAME_PAUSED);
    }
}
