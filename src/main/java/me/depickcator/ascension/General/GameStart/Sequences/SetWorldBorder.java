package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.TeamUtil;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

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
