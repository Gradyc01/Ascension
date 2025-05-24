package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;

import javax.sound.midi.Sequence;

public class CallLocationCheck extends GameSequences {

    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
        plugin.setLocationCheck(new LocationCheck(g.getSpawnCoordsLocation()));

        game.callback();
    }
}
