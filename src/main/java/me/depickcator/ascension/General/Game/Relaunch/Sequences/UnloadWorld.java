package me.depickcator.ascension.General.Game.Relaunch.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
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

        game.callback(80 * 20);
    }
}
