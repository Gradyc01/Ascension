package org.bingo4.General.Game;

import org.bingo4.Bingo4;

public abstract class GameSequences {
    protected final Bingo4 plugin;
    public GameSequences() {
        plugin = Bingo4.getInstance();
    }
    /*Runs a certain sequence of events that help start the game
    * Calls the callback at the end*/
    public abstract void run(GameLauncher game);
}
