package me.depickcator.ascension.General.GameStart;

import me.depickcator.ascension.Ascension;

public abstract class GameStartSequence {
    protected final Ascension plugin;
    public GameStartSequence() {
        plugin = Ascension.getInstance();
    }
    public abstract void run(StartGame game);
}
