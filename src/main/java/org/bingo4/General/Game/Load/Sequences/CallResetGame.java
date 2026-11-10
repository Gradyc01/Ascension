package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Load.LoadGame;
import org.bingo4.General.Game.Reset.ResetGame;

public class CallResetGame extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
        new ResetGame(false, g.getSpawnCoordsLocation(), g.getSeed());
        game.callback();
    }
}
