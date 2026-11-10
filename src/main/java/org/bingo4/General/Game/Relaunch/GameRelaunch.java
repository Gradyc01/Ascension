package org.bingo4.General.Game.Relaunch;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Relaunch.Sequences.DeleteWorld;
import org.bingo4.General.Game.Relaunch.Sequences.KickPlayers;
import org.bingo4.General.Game.Relaunch.Sequences.UnloadWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class GameRelaunch extends GameLauncher {
    public GameRelaunch() {
        super();
        plugin.getServer().setWhitelist(true);
        start();
    }

    @Override
    protected List<GameSequences> initSequence() {
        List<World> worlds = new ArrayList<>(List.of(
                        plugin.getWorld(),
                        plugin.getNether(),
                        Bukkit.getWorld("world_the_end")));
        return List.of(
                new KickPlayers(),
                new UnloadWorld(worlds),
                new DeleteWorld(worlds)
        );
    }

    @Override
    protected boolean canStart() {
        return true;
    }

    @Override
    protected void end() {
        plugin.getServer().setWhitelist(false);
        plugin.getServer().restart();
    }
}
