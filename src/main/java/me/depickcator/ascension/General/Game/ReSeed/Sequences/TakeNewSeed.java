package me.depickcator.ascension.General.Game.ReSeed.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Persistence.SeedChooser;

public class TakeNewSeed extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        SeedChooser seedChooser = new SeedChooser();
        seedChooser.findSeed();
        game.callback(0);
    }
}
