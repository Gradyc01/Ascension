package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoData;

public class SetBingoData extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        plugin.setBingoData(new BingoData(plugin));

        game.callback();
    }
}
