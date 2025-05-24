package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import org.bukkit.entity.ArmorStand;

import static me.depickcator.ascension.General.BuildLobby.fillArea;

public class BuildPlatform extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
        ArmorStand spawnCoordsArmorStand = g.getSpawnCoordsArmorStand();
        String quartzBlock = "quartz_block";
        fillArea(2, 2, 2, -2, 2, -2, quartzBlock, spawnCoordsArmorStand);
        fillArea(1, 2, 3, -1, 2, 3, quartzBlock, spawnCoordsArmorStand);
        fillArea(1, 2, -3, -1, 2, -3, quartzBlock, spawnCoordsArmorStand);
        fillArea(3, 2, 1, 3, 2, -1, quartzBlock, spawnCoordsArmorStand);
        fillArea(-3, 2, 1, -3, 2, -1, quartzBlock, spawnCoordsArmorStand);

        String grassBlock = "grass_block";
        fillArea(-5, 1, 2, 5, 1, -2, grassBlock, spawnCoordsArmorStand);
        fillArea(-2, 1, 5, 2, 1, -5, grassBlock, spawnCoordsArmorStand);
        fillArea(-3, 1, 4, 3, 1, -4, grassBlock, spawnCoordsArmorStand);
        fillArea(4, 1, 3, -4, 1, -3, grassBlock, spawnCoordsArmorStand);

        fillArea(-7, 0, 4, 7, 0, -4, grassBlock, spawnCoordsArmorStand);
        fillArea(-4, 0, 7, 4, 0, -7, grassBlock, spawnCoordsArmorStand);
        fillArea(-5, 0, 6, 5, 0, -6, grassBlock, spawnCoordsArmorStand);
        fillArea(6, 0, 5, -6, 0, -5, grassBlock, spawnCoordsArmorStand);

        fillArea(-9, -1, 6, 9, -1, -6, grassBlock, spawnCoordsArmorStand);
        fillArea(-6, -1, 9, 6, -1, -9, grassBlock, spawnCoordsArmorStand);
        fillArea(-7, -1, 8, 7, -1, -8, grassBlock, spawnCoordsArmorStand);
        fillArea(8, -1, 7, -8, -1, -7, grassBlock, spawnCoordsArmorStand);

        fillArea(-12, -2, 9, 12, -2, -9, grassBlock, spawnCoordsArmorStand);
        fillArea(-9, -2, 12, 9, -2, -12, grassBlock, spawnCoordsArmorStand);
        fillArea(-10, -2, 11, 10, -2, -11, grassBlock, spawnCoordsArmorStand);
        fillArea(11, -2, 10, -11, -2, -10, grassBlock, spawnCoordsArmorStand);

        fillArea(-16, -3, 13, 16, -3, -13, grassBlock, spawnCoordsArmorStand);
        fillArea(-13, -3, 16, 13, -3, -16, grassBlock, spawnCoordsArmorStand);
        fillArea(-14, -3, 15, 14, -3, -15, grassBlock, spawnCoordsArmorStand);
        fillArea(15, -3, 14, -15, -3, -14, grassBlock, spawnCoordsArmorStand);
        game.callback();
    }
}
