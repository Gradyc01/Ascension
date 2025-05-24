package me.depickcator.ascension.General.Game;

import me.depickcator.ascension.Ascension;

public abstract class GameSequences {
    protected final Ascension plugin;
    public GameSequences() {
        plugin = Ascension.getInstance();
    }
    /*Runs a certain sequence of events that help start the game
    * Calls the callback at the end*/
    public abstract void run(GameLauncher game);
}
