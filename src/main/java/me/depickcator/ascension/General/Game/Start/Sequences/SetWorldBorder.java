package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import org.bukkit.Location;
import org.bukkit.WorldBorder;


public class SetWorldBorder extends GameSequences {
    private final int radius;
    public SetWorldBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void run(GameLauncher game) {
        WorldBorder worldBorder = plugin.getWorld().getWorldBorder();
        Location loc = Ascension.getSpawn();
        worldBorder.setCenter(loc.getX(), loc.getZ());
        worldBorder.setSize(radius * 2, 0); // Ex 6k would be 3k x 3k
        game.callback();
    }
}
