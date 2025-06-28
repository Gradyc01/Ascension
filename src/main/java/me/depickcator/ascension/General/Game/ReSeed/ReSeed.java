package me.depickcator.ascension.General.Game.ReSeed;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.General.Game.ReSeed.Sequences.TakeNewSeed;
import me.depickcator.ascension.General.Game.Relaunch.GameRelaunch;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.DeleteWorld;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.KickPlayers;
import me.depickcator.ascension.General.Game.Relaunch.Sequences.UnloadWorld;
import me.depickcator.ascension.General.Game.Reset.ResetGame;
import me.depickcator.ascension.Persistence.SettingsReader;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
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
