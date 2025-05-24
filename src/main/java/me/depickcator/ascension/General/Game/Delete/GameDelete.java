package me.depickcator.ascension.General.Game.Delete;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Relaunch.GameRelaunch;
import me.depickcator.ascension.Persistence.DeleteSettings;
import me.depickcator.ascension.Utility.TextUtil;

import java.util.List;

public class GameDelete extends GameLauncher {
    public GameDelete() {
        super();
        start();
    }
    @Override
    protected List<GameSequences> initSequence() {
        return List.of(
                new DeleteSettings()
        );
    }

    @Override
    protected boolean canStart() {
        return true;
    }

    @Override
    protected void end() {
        new GameRelaunch();
    }
}
