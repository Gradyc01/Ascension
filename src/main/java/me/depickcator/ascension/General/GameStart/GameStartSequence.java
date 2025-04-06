package me.depickcator.ascension.General.GameStart;

import me.depickcator.ascension.Ascension;

public abstract class GameStartSequence {
    protected final Ascension plugin;
    public GameStartSequence() {
        plugin = Ascension.getInstance();
    }
    /*Runs a certain sequence of events that help start the game
    * Calls the callback at the end*/
    public abstract void run(StartGame game);
}
