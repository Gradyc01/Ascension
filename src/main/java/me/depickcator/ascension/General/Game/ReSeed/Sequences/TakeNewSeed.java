package me.depickcator.ascension.General.Game.ReSeed.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.ReSeed.ReSeed;
import me.depickcator.ascension.Persistence.SeedChooser;

public class TakeNewSeed extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (game instanceof ReSeed g) {
            SeedChooser seedChooser = new SeedChooser();
            g.setNewGameWorld(seedChooser.findSeed());
            g.callback(0);
        } else {
            throw new IllegalArgumentException("Not the right GameLauncher");
        }

    }
}
