package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Interfaces.EntityInteraction;

public class ClearEntityInteractions extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        EntityInteraction.clearInteractions();
        game.callback();
    }
}
