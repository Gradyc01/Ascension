package org.bingo4.Timeline.Events.NetherManager;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.General.Game.Reset.Sequences.LoadGameRules;
import org.bingo4.Persistence.SeedChooser;
import org.bingo4.Persistence.WorldDownloader;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.Scoreboards.GameBoard;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.NetherKoth.NetherKothInstance;
import org.bingo4.Utility.TextUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class NetherManager {

    private final List<NetherKothInstance> netherInstances;
    private final Bingo4 plugin = Bingo4.getInstance();
    private final Set<PlayerData> playerDataSet;
    private BukkitTask netherLoop;
    private int timePassed;
    private static final int TOTAL_SECONDS = 360;

    public NetherManager() {
        this.netherInstances = new ArrayList<>();
        playerDataSet = new HashSet<>();
        timePassed = 0;
    }

    public void setNetherInstances(List<CampInstance> campInstances) {
        netherInstances.clear();

        for (CampInstance camp : campInstances) {
            if (camp instanceof NetherKothInstance kothInstance) {
                netherInstances.add(kothInstance);
            }
        }

        findNewSeed()
                .thenCompose(seed -> {
                    UUID uuid = UUID.randomUUID();

                    return downloadWorld(seed, uuid)
                            .thenApply(success -> {
                                if (!success) {
                                    throw new IllegalStateException(
                                            "Failed to download Nether world: " + uuid
                                    );
                                }
                                return seed;
                            })
                            .thenApply(downloadedSeed -> new Object[]{downloadedSeed, uuid});
                })
                .thenAcceptAsync(data -> {
                    Long seed = (Long) data[0];
                    UUID uuid = (UUID) data[1];

                    World world = createNewWorld(seed, uuid);
                    plugin.setNether(world);
                    LoadGameRules.loadGameRules(world);
                    world.getWorldBorder().setSize(200);
                    world.getWorldBorder().setCenter(Bingo4.getSpawn().getBlockX(), Bingo4.getSpawn().getBlockZ());

                }, task -> Bukkit.getScheduler().runTask(plugin, task))
                .exceptionally(ex -> {
                    plugin.getLogger().severe(
                            "Failed to initialize Nether world: " + ex.getMessage()
                    );
                    throw new RuntimeException(ex);
                });
    }

    public void start() {
        plugin.getGameState().setCurrentState(GameStates.GAME_NETHER_OPEN);

        for (NetherKothInstance instance : netherInstances) {
            instance.start();
        }
        portalLoop();
    }

    private void portalLoop() {
        timePassed = 0;
        netherLoop = new BukkitRunnable() {
            @Override
            public void run() {

                for (NetherKothInstance instance : netherInstances) {
                    instance.portalLoop(timePassed, TOTAL_SECONDS);
                }

                for (PlayerData pD : playerDataSet) {
                    if (pD.getPlayerScoreboard().getBoards() instanceof GameBoard gameBoard) {
                        gameBoard.updateTime();
                    }
                }

                if (timePassed++ >= TOTAL_SECONDS) {
                    cancel();
                    stop();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void stop() {
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        for (NetherKothInstance instance : netherInstances) {
            instance.stop();
        }
        if (netherLoop != null) netherLoop.cancel();

        for (PlayerData pD : playerDataSet) {
            Player player = pD.getPlayer();
//            = PlayerUtil.getPlayerData(player);
//            if (pD == null) continue;
            pD.getPlayerScoreboard().makeGameBoard();
            Location newLocation = player.getLocation();
            newLocation.setWorld(plugin.getWorld());
            newLocation.setY(plugin.getWorld().getHighestBlockYAt(newLocation.getBlockX(), newLocation.getBlockZ()));
            player.teleport(newLocation);
        }
        playerDataSet.clear();
    }

    public int getTimeRemaining() {
        return TOTAL_SECONDS - timePassed;
    }

    public boolean addPlayerData(PlayerData playerData) {
        return playerDataSet.add(playerData);
    }

    public boolean removePlayerData(PlayerData playerData) {
        return playerDataSet.remove(playerData);
    }

    private CompletableFuture<Long> findNewSeed() {
        CompletableFuture<Long> future = new CompletableFuture<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    SeedChooser seedChooser = new SeedChooser();
                    future.complete(seedChooser.findNetherSeed());
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        }.runTaskAsynchronously(plugin);

        return future;
    }

    private CompletableFuture<Boolean> downloadWorld(Long seed, UUID uuid) {
        WorldDownloader worldDownloader = new WorldDownloader();

        return worldDownloader.downloadWorld(
                seed.toString(),
                uuid.toString(),
                "nethers"
        );
    }

    private World createNewWorld(Long seed, UUID uuid) {
        WorldCreator worldCreator = new WorldCreator(
                new NamespacedKey(plugin, uuid.toString())
        );

        worldCreator.environment(World.Environment.NETHER);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.seed(seed);
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