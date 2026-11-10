package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;

public class ReloadLobby extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        plugin.getLobby().updateLobby();
        game.callback();
    }
}
