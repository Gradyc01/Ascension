package org.bingo4.General.Game.Relaunch.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class UnloadWorld extends GameSequences {
    private final List<World> worlds;
    public UnloadWorld(List<World> worlds) {
        this.worlds = worlds;
    }

    @Override
    public void run(GameLauncher game) {
        for (World world : worlds) {
            Bukkit.unloadWorld(world, false);
            TextUtil.debugText("Unloading world: " + world.getName());
        }

        game.callback(5 * 20);
    }
}
