package org.bingo4.General.Game.ReSeed.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.ReSeed.ReSeed;
import org.bingo4.Persistence.SeedChooser;

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
