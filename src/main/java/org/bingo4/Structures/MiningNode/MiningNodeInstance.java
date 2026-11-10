package org.bingo4.Structures.MiningNode;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.AscensionBlockGUI;
import org.bingo4.Interfaces.RealmNMSMob;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.MainMenuUI.Map.MapItems;
import org.bingo4.Mobs.ItemDisplayDetection;
import org.bingo4.Structures.CampInstance;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Structures.MiningNode.LootTables.MiningNodeCampLoot;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.TextUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static org.bingo4.MainMenuUI.Map.MapItem.BINGO_TURN_IN;

public class MiningNodeInstance extends CampInstance {

    private static final int MIN_SPAWN_DISTANCE = 3;
    private static final int MAX_SPAWN_DISTANCE = 5;

    // TODO Adjust these animation durations.
    private static final long MINING_ANIMATION_TICKS = 60L;
    private static final long BETWEEN_WAVES_TICKS = 30L;
    private static final long REWARD_ANIMATION_TICKS = 80L;

    private static final int SPAWN_LOCATION_ATTEMPTS = 40;

    private final Random random = new Random();

    private Set<Block> blocks;
    private MapItem mapItem;

    // Each inner list represents the mob types that can spawn in one wave.
    private final List<List<RealmNMSMob>> waves = new ArrayList<>();

    // Mobs currently alive in the active wave.
    private final Set<LivingEntity> activeMobs = new HashSet<>();

    private BukkitTask animationTask;
    private BukkitTask mobCheckTask;

    private boolean running;
    private boolean completed;

    private int currentWaveIndex;
    private AscensionBlockGUI gui;

    private FloodBlocks floodBlocks;
    private final MiningNodeCampLoot loot;

    public MiningNodeInstance(AbstractCampType type, Location origin, Location center, MiningNodeCampLoot loot) {
        super(type, origin, center);
        this.loot = loot;
    }

    /*
     * Called after the structure has finished being placed.
     */
    public void buildFinished(Set<Block> blocks) {

        this.blocks = blocks;

        for (Block block : blocks) {
            if (block.getBlockData().getMaterial().equals(Material.COMMAND_BLOCK)) {
                TextUtil.debugText("[Mining Node] Found command block creating gui");
                gui = new MiningNodeBlockGUI(block, this);
                break;
            }
        }

        mapItem = new MapItem("Probe", getCenter(), loot.getMapId());
        floodBlocks = new FloodBlocks(getCenter().clone().add(0, -1, 0), 1.1, loot);

        MapItems mapItems = plugin.getSettingsUI()
                .getSettings()
                .getTimeline()
                .getMapItems();

        World world = getCenter().getWorld();
        setWaves(loot.getSpawnWaves(world, random));

        mapItems.addMapItem(mapItem);
    }

    /*
     * Configure the waves for this particular mining node.
     *
     * Each inner list contains the different mob types for that wave.
     */
    public void setWaves(List<List<RealmNMSMob>> waveDefinitions) {

        if (running) {
            throw new IllegalStateException("Cannot change waves while mining node is running.");
        }

        waves.clear();

        for (List<RealmNMSMob> wave : waveDefinitions) {
            waves.add(new ArrayList<>(wave));
        }
    }

    /*
     * Starts the entire mining node encounter.
     */
    public void start() {

        if (running || completed) {
            return;
        }

        if (waves.isEmpty()) {
            // TODO Log that no waves have been configured.
            return;
        }

        running = true;
        currentWaveIndex = 0;
        floodBlocks.start(random);

        activeMobs.clear();

        beginWave();
    }

    /*
     * Starts the current wave's animation and then spawns its mobs.
     */
    private void beginWave() {

        if (!running) {
            return;
        }

        // All waves have been completed.
        if (currentWaveIndex >= waves.size()) {
            finishMiningNode();

            return;
        }

        int waveNumber = currentWaveIndex + 1;

        playMiningAnimation(waveNumber);

        animationTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {

            animationTask = null;

            if (!running) {
                return;
            }

            spawnWave(waves.get(currentWaveIndex));

            startMobMonitor();

        }, MINING_ANIMATION_TICKS);
    }

    /*
     * Spawns all the mob types assigned to a particular wave.
     *
     * Each type is guaranteed at least one spawn attempt.
     * Additional mobs are determined by RNG.
     */
    private void spawnWave(List<RealmNMSMob> mobTypes) {

        activeMobs.clear();

        for (RealmNMSMob mobType : mobTypes) {

            if (mobType == null) {
                continue;
            }

            // At least one mob of this type.
            // RNG determines how many additional mobs spawn.
            int mobCount = 1;

            for (int i = 0; i < mobCount; i++) {

                Location spawnLocation = findSpawnLocation();

                if (spawnLocation == null) {
                    // TODO Implement a fallback when no valid spawn location is found.
                    continue;
                }

                spawnLocation.getWorld().spawnParticle(Particle.SONIC_BOOM, spawnLocation, 1, 0, 0, 0, 0);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        LivingEntity mob = mobType.spawn(spawnLocation);

                        if (mob != null) {
                            activeMobs.add(mob);
                        }
                    }
                }.runTaskLater(plugin, 7);
            }
        }
    }

    /*
     * Periodically checks whether all mobs in the current wave are dead.
     */
    private void startMobMonitor() {

        mobCheckTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            if (!running) {
                cancelMobCheckTask();
                return;
            }

            // Remove dead, despawned, or otherwise invalid entities.
            activeMobs.removeIf(mob ->
                    mob == null || mob.isDead() || !mob.isValid()
            );

            // The entire wave has been defeated.
            if (activeMobs.isEmpty()) {

                cancelMobCheckTask();

                currentWaveIndex++;

                // Small delay before starting the next wave.
                animationTask = Bukkit.getScheduler().runTaskLater(
                        plugin,
                        () -> {
                            animationTask = null;
                            beginWave();
                        },
                        BETWEEN_WAVES_TICKS
                );
            }

        }, 20L, 10L);
    }

    /*
     * Finds a suitable spawn location between 5 and 15 blocks
     * horizontally from the mining node's center.
     */
    private Location findSpawnLocation() {

        Location center = getCenter();

        if (center == null || center.getWorld() == null) {
            return null;
        }

        World world = center.getWorld();

        for (int attempt = 0; attempt < SPAWN_LOCATION_ATTEMPTS; attempt++) {

            // Random direction.
            double angle = random.nextDouble() * Math.PI * 2;

            // Random distance between 5 and 15 blocks.
            double distance = MIN_SPAWN_DISTANCE
                    + random.nextDouble() * (MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE);

            double xOffset = Math.cos(angle) * distance;
            double zOffset = Math.sin(angle) * distance;

            int x = (int) Math.floor(center.getX() + xOffset);
            int z = (int) Math.floor(center.getZ() + zOffset);

            // Check the actual block-center distance.
            double dx = (x + 0.5) - center.getX();
            double dz = (z + 0.5) - center.getZ();

            double actualDistance = Math.sqrt(dx * dx + dz * dz);

            if (actualDistance < MIN_SPAWN_DISTANCE
                    || actualDistance > MAX_SPAWN_DISTANCE) {
                continue;
            }

            // Find the highest block at this X/Z coordinate.
            Block ground = world.getHighestBlockAt(x, z);

            Block feet = ground.getRelative(0, 1, 0);
            Block head = ground.getRelative(0, 2, 0);

            // Ground must be solid.
            if (!ground.getType().isSolid()) {
                continue;
            }

            // There must be enough room for the mob.
            if (!feet.isPassable() || !head.isPassable()) {
                continue;
            }

            // Avoid spawning inside liquids.
            if (feet.isLiquid() || head.isLiquid()) {
                continue;
            }

            // TODO Add additional checks for:
            // - Minimum distance between spawned mobs.
            // - Dangerous blocks or unsuitable terrain.
            // - Protected regions.
            // - Spawn locations inside the mining node structure.

            return new Location(
                    world,
                    x + 0.5,
                    ground.getY() + 1,
                    z + 0.5,
                    random.nextFloat() * 360,
                    0
            );
        }

        return null;
    }

    /*
     * Called once every wave has been defeated.
     */
    private void finishMiningNode() {

        if (!running) {
            return;
        }

        playRewardAnimation();

        animationTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {

            animationTask = null;

            if (!running) {
                return;
            }

            // Give the player/team their reward.
            giveReward();
            stop();

            running = false;
            completed = true;

        }, REWARD_ANIMATION_TICKS);
    }

    /*
     * Plays the digging animation before a wave appears.
     */
    private void playMiningAnimation(int waveNumber) {
        // TODO Implement mining/digging animation.
        // waveNumber can be used for animation variations.
        new BukkitRunnable() {
            int times = 3;
            @Override
            public void run() {
                if (times-- <= 0) {
                    cancel();
                    return;
                }
                floodBlocks.flood(random);
                getOrigin().getWorld().playSound(getCenter(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 0);
            }
        }.runTaskTimer(plugin, 20, 40);

    }

    /*
     * Plays the reward animation after the final wave.
     */
    private void playRewardAnimation() {
        getCenter().getWorld().playSound(getCenter(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 1f);

        new BukkitRunnable() {

            @Override
            public void run() {
                TextUtil.debugText("[Bingo Turn In] Destroying Build at " + TextUtil.formatLocation(getOrigin()));
                if (blocks.isEmpty()) {
                    cancel();
                    getCenter().getWorld().spawnParticle(Particle.EXPLOSION, getCenter().clone().add(0, 2,0), 200, 5, 5, 5);
                    getCenter().getWorld().playSound(getCenter(), Sound.ENTITY_GENERIC_EXPLODE, 10f, 0f);
                    return;
                }

                int i = 10;
                for (Block b : new HashSet<>(blocks)) {
                    if (i-- <= 0) {
                        break;
                    }
                    blocks.remove(b);

                    b.removeMetadata("UNBREAKABLE", Bingo4.getInstance());
                    b.setType(Material.AIR);
                }

                getOrigin().getWorld().playSound(getCenter(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 0);
            }
        }.runTaskTimer(Bingo4.getInstance(), 20, 20);
    }

    /*
     * Handles the final reward distribution.
     */
    private void giveReward() {

        Location center = getCenter();

        double radius = 2.0;
        double yOffset = 1.0;

        List<ItemStack> rewards = loot.populateLoot(null, random, 1.0).stream().toList();
        int count = rewards.size();

        for (int i = 0; i < count; i++) {

            double angle = (2 * Math.PI * i) / count;

            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;

            Location displayLocation = center.clone().add(
                    x,
                    yOffset,
                    z
            );

            new ItemDisplayDetection(displayLocation, rewards.get(i));
        }
    }

    /*
     * Stops the mining node and cancels all scheduled tasks.
     */
    public void stop() {

        running = false;

        if (animationTask != null) {
            animationTask.cancel();
            animationTask = null;
        }

        cancelMobCheckTask();

        // Remove mobs belonging to this mining node.
        for (LivingEntity mob : activeMobs) {

            if (mob != null && mob.isValid()) {
                mob.remove();
            }
        }

        activeMobs.clear();
        plugin.getSettingsUI().getSettings().getTimeline().getMapItems().removeMapItem(mapItem);
        plugin.getSettingsUI().getSettings().getTimeline().getCampManager().removeCamp(this);

        // TODO Decide whether stopping should also reset wave progress.
    }

    private void cancelMobCheckTask() {

        if (mobCheckTask != null) {
            mobCheckTask.cancel();
            mobCheckTask = null;
        }
    }

    public MapItem getMapItem() {
        return mapItem;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getCurrentWave() {
        return currentWaveIndex + 1;
    }

    public int getTotalWaves() {
        return waves.size();
    }
}
