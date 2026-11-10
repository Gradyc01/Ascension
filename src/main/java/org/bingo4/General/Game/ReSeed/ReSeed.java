package org.bingo4.General.Game.ReSeed;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.ReSeed.Sequences.TakeNewSeed;
import org.bingo4.General.Game.Reset.ResetGame;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

import java.util.List;

public class ReSeed extends GameLauncher {

    private Pair<Location, Long> newGameWorld;
    public ReSeed() {
        super();
        start();
    }
    @Override
    protected List<GameSequences> initSequence() {
        return List.of(
                new TakeNewSeed()
        );
    }

    @Override
    protected boolean canStart() {
        return true;
    }

    @Override
    protected void end() {
        if (newGameWorld != null) {
            new ResetGame(false, newGameWorld.getLeft(), newGameWorld.getRight());
        } else {
            TextUtil.broadcastMessage(TextUtil.makeText("Reseeding Failed", TextUtil.RED));
        }
    }

    public void setNewGameWorld(Pair<Location, Long> newGameWorld) {
        this.newGameWorld = newGameWorld;
    }
}
