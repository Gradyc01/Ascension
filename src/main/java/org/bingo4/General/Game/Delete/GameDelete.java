package org.bingo4.General.Game.Delete;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Relaunch.GameRelaunch;
import org.bingo4.Persistence.DeleteSettings;

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
