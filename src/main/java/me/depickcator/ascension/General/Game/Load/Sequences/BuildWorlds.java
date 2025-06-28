package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.*;

import java.util.UUID;


public class BuildWorlds extends GameSequences {
    private final Location loc;
    private final long seed;
    public BuildWorlds(Location loc, long seed) {
        this.loc = loc;
        this.seed = seed;
    }
    @Override
    public void run(GameLauncher game) {
        UUID uuid = UUID.randomUUID();
        World overworld = createNewWorld(World.Environment.NORMAL, seed, uuid);
        plugin.setWorld(overworld);
        plugin.setNether(createNewWorld(World.Environment.NETHER, seed, uuid));
        game.callback();
    }

    public World createNewWorld(World.Environment env, Long seed, UUID uuid) {
        WorldCreator worldCreator = new WorldCreator("./worlds/" + env.name() + "_" + uuid);

        worldCreator.environment(env); // You can change this (e.g., to NETHER or THE_END)
        worldCreator.type(WorldType.NORMAL); // Use different world types like FLAT, LARGE_BIOMES, etc.
        TextUtil.debugText("Creating world with seed: " + seed);
        worldCreator.seed(seed);
        World newWorld = worldCreator.createWorld(); // Actually creates the world

        // Optionally, you can teleport a player to this world or manipulate it further
        TextUtil.debugText("New world '" + uuid + "' created successfully!");
        return newWorld;
    }
}
