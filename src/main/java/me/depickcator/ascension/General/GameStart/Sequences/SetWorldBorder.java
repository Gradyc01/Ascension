package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import org.bukkit.WorldBorder;


public class SetWorldBorder extends GameStartSequence {
    private final int radius;
    public SetWorldBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void run(StartGame game) {
        WorldBorder worldBorder = plugin.getWorld().getWorldBorder();
        worldBorder.setSize(radius * 2, 0); // Ex 6k would be 3k x 3k
        game.callback();
    }
}
