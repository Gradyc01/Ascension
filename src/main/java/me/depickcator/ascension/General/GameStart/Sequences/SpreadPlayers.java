package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;

public class SpreadPlayers extends GameStartSequence {
    private final int radius;
    public SpreadPlayers(int radius) {
        this.radius = radius;
    }

    @Override
    public void run(StartGame game) {
        Location loc = Ascension.getSpawn();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "spreadplayers " + loc.getBlockX() + " " + loc.getBlockZ() + " " + (radius / 6) + " " + radius + " true @a");
        game.callback();
    }
}
