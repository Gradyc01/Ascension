package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Interfaces.EntityInteraction;

public class ClearEntityInteractions extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        EntityInteraction.clearInteractions();
        game.callback();
    }
}
