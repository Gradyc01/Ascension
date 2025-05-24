package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Reset.ResetGame;

public class CallResetGame extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        new ResetGame();
        game.callback();
    }
}
