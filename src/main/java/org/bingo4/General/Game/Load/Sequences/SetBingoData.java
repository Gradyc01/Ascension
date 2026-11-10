package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.MainMenuUI.BingoBoard.BingoData;

public class SetBingoData extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        plugin.setBingoData(new BingoData(plugin));

        game.callback();
    }
}
