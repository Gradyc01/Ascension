package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.LocationChecker.LocationCheck;

public class CallLocationCheck extends GameSequences {

    @Override
    public void run(GameLauncher game) {
//        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
//        LoadGame g = (LoadGame) game;
//        plugin.setLocationCheck(new LocationCheck(g.getSpawnCoordsLocation()));
        plugin.setLocationCheck(new LocationCheck(Bingo4.getSpawn()));
        game.callback();
    }
}
