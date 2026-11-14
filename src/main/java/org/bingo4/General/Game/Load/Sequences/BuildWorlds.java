package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Persistence.WorldDownloader;
import org.bingo4.Utility.TextUtil;

import org.bukkit.*;

import java.util.UUID;

public class BuildWorlds extends GameSequences {

    private final Location loc;
    private final long seed;

    private final WorldDownloader downloader;

    public BuildWorlds(Location loc, long seed) {
        this.loc = loc;
        this.seed = seed;
        this.downloader = new WorldDownloader();
    }

    @Override
    public void run(GameLauncher game) {
        UUID uuid = UUID.randomUUID();

        TextUtil.debugText("Preparing world for seed: " + seed);
        // Download the selected seed asynchronously.
        downloader.downloadWorld(seed + "", uuid.toString()).thenAccept(success -> {
            // Return to the Bukkit main thread.
            Bukkit.getScheduler().runTask(plugin, () -> {

                if (!success) {
                    plugin.getLogger().severe(
                            "Failed to download world: " + seed
                    );
                    return;
                }

                try {

                    World overworld = createNewWorld(
                            World.Environment.NORMAL,
                            seed,
                            uuid
                    );

                    plugin.setWorld(overworld);

                    TextUtil.debugText(
                            "World loaded successfully: " + seed
                    );

                    game.callback();

                } catch (Exception e) {
                    plugin.getLogger().severe(
                            "Failed to create world: " + seed
                    );
                    e.printStackTrace();
                }
            });

        }).exceptionally(error -> {
            plugin.getLogger().severe(
                    "World download error: " + error.getMessage()
            );
            return null;
        });
    }

    public World createNewWorld(
            World.Environment env,
            Long seed,
            UUID uuid
    ) {

        WorldCreator worldCreator = new WorldCreator(
                new NamespacedKey(Bingo4.getInstance(), uuid.toString())
        );

        worldCreator.environment(env);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.seed(seed);

        TextUtil.debugText(
                "Creating world with seed: " + seed
        );

        World newWorld = worldCreator.createWorld();

        if (newWorld == null) {
            throw new IllegalStateException(
                    "Failed to create world: " + uuid
            );
        }

        TextUtil.debugText(
                "New world '" + newWorld.getWorldPath()
                        + "' created successfully!"
        );

        return newWorld;
    }
}
