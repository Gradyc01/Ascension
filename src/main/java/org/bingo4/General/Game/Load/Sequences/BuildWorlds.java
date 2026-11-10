package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

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

        WorldCreator worldCreator = new WorldCreator(
                "./worlds/" + env.name() + "_" + uuid
        );

        worldCreator.environment(env);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.seed(seed);

        TextUtil.debugText("Creating world with seed: " + seed);

        World newWorld = worldCreator.createWorld();

        if (newWorld == null) {
            throw new IllegalStateException("Failed to create world: " + uuid);
        }

        TextUtil.debugText("New world '" + uuid + "' created successfully!");

        return newWorld;
    }

//    public World createNewWorld(World.Environment env, Long seed, UUID uuid) {
//        WorldCreator worldCreator = new WorldCreator("./worlds/" + env.name() + "_" + uuid);
//
//
//        worldCreator.environment(env); // You can change this (e.g., to NETHER or THE_END)
//        worldCreator.type(WorldType.NORMAL); // Use different world types like FLAT, LARGE_BIOMES, etc.
//        TextUtil.debugText("Creating world with seed: " + seed);
//        worldCreator.seed(seed);
//        World newWorld = worldCreator.createWorld(); // Actually creates the world
//
//        // Optionally, you can teleport a player to this world or manipulate it further
//        TextUtil.debugText("New world '" + uuid + "' created successfully!");
//        return newWorld;
//    }

}
