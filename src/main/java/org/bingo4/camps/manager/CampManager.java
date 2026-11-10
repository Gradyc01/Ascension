package org.bingo4.camps.manager;

import org.bingo4.Bingo4;
import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.Utility.TextUtil;
import org.bingo4.camps.api.*;
import org.bingo4.camps.core.Site;
import org.bingo4.Structures.TerrainAnalyzer;
import org.bingo4.camps.core.TerrainBlender;
import org.bingo4.camps.storage.CampRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.data.Directional;

import java.util.*;

/**
 * Registers camp types, discovers suitable sites around a center, places camps,
 * and keeps the resulting CampInstances grouped by camp type.
 *
 * All methods that inspect or modify the Bukkit world must be called on the
 * server thread. This class deliberately does not force-load chunks.
 */
public final class CampManager {
    private final Bingo4 plugin;
    private final TerrainAnalyzer terrainAnalyzer;
    private final TerrainBlender terrainBlender;
    private final CampRegistry registry = new CampRegistry();

    private final Map<String, AbstractCampType> types = new LinkedHashMap<>();
    private final Map<String, List<CampInstance>> campsByType = new LinkedHashMap<>();

    private final int blendRadius;
    private final int minimumSpacing;
    private final int maxCandidateAttempts;

    public CampManager(Bingo4 plugin, Collection<? extends AbstractCampType> campTypes) {
        this(plugin, campTypes, 4, 2, 4, 24, 10_000);
    }

    public CampManager(
            Bingo4 plugin,
            Collection<? extends AbstractCampType> campTypes,
            int maxHeightDifference,
            int maxNeighbourStep,
            int blendRadius,
            int minimumSpacing,
            int maxCandidateAttempts
    ) {
        this.plugin = plugin;
        if (blendRadius < 0 || minimumSpacing < 16 || maxCandidateAttempts < 1) {
            throw new IllegalArgumentException("Invalid camp manager configuration");
        }

        this.terrainAnalyzer = new TerrainAnalyzer(maxHeightDifference, maxNeighbourStep);
        this.terrainBlender = new TerrainBlender();
        this.blendRadius = blendRadius;
        this.minimumSpacing = minimumSpacing;
        this.maxCandidateAttempts = maxCandidateAttempts;

        if (campTypes != null) {
            for (AbstractCampType type : campTypes) {
                registerType(type);
            }
        }
    }

    /** Register a camp category. Each category must have a unique ID. */
    public void registerType(AbstractCampType type) {
        Objects.requireNonNull(type, "type");
        String id = type.getId();
        if (types.putIfAbsent(id, type) != null) {
            throw new IllegalArgumentException("Camp type already registered: " + id);
        }
        campsByType.put(id, new ArrayList<>());
    }

    public Optional<AbstractCampType> getType(String id) {
        return Optional.ofNullable(types.get(id));
    }

    public Collection<AbstractCampType> getTypes() {
        return Collections.unmodifiableCollection(types.values());
    }

    /**
     * Finds up to {@code amount} suitable base footprints whose centers are
     * within {@code radius} blocks of {@code center}. The footprint is a square large
     * enough for the biggest base of any registered template, in any rotation.
     *
     * This method only searches; it does not paste structures or modify terrain.
     * It returns fewer than requested if the terrain is unsuitable or the
     * relevant chunks are not already loaded.
     */
    public List<CampLocation> findCampLocations(int amount, int radius, Location center) {
        assertMainThread();
        if (amount <= 0) return List.of();
        if (radius < 0) throw new IllegalArgumentException("radius cannot be negative");
        if (center == null || center.getWorld() == null) {
            throw new IllegalArgumentException("A center with a world is required");
        }
        if (types.isEmpty()) return List.of();

        World world = center.getWorld();
        int structureHeight = types.values().stream()
                .map(AbstractCampType::getTemplate)
                .mapToInt(CampTemplate::height)
                .max()
                .orElse(1);

        // Search footprint: big enough for any registered base in any rotation.
        int footprint = types.values().stream()
                .map(AbstractCampType::getTemplate)
                .mapToInt(t -> Math.max(t.width(), t.depth()))
                .max()
                .orElse(16);
        // The structure can overhang the base; its chunks must be loaded before pasting.
        int loadMargin = Math.max(blendRadius, types.values().stream()
                .map(AbstractCampType::getTemplate)
                .mapToInt(CampTemplate::structureMargin)
                .max()
                .orElse(0));
        double halfFootprint = (footprint - 1) / 2.0;

        long seed = world.getSeed()
                ^ ((long) center.getBlockX() * 0x9E3779B97F4A7C15L)
                ^ ((long) center.getBlockZ() * 0xC2B2AE3D27D4EB4FL)
                ^ ((long) radius << 32)
                ^ amount;
        SplittableRandom random = new SplittableRandom(seed);

        List<CampLocation> found = new ArrayList<>(amount);
        long desiredAttempts = Math.max(100L, (long) amount * 120L);
        int attempts = (int) Math.min(maxCandidateAttempts, desiredAttempts);

        for (int attempt = 0; attempt < attempts && found.size() < amount; attempt++) {
            // sqrt(random) gives a uniform distribution over the circular area.
            double distance = radius == 0 ? 0 : Math.sqrt(random.nextDouble()) * radius;
            double angle = random.nextDouble(0.0, Math.PI * 2.0);
            double targetX = center.getX() + Math.cos(angle) * distance;
            double targetZ = center.getZ() + Math.sin(angle) * distance;

            // The sampled point is the camp center; convert it to the footprint's
            // minimum X/Z corner.
            int x = (int) Math.floor(targetX - halfFootprint);
            int z = (int) Math.floor(targetZ - halfFootprint);

            if (!isFarEnough(world, x, z, footprint, found)) continue;
            if (!chunksLoaded(world, x - loadMargin, z - loadMargin,
                    x + footprint - 1 + loadMargin, z + footprint - 1 + loadMargin)) {
                continue;
            }

            Optional<Site> evaluated = terrainAnalyzer.evaluate(
                    world, x, z, structureHeight, footprint, footprint);
            if (evaluated.isEmpty()) continue;

            Site site = evaluated.get();
            double centerDx = (x + halfFootprint) - center.getX();
            double centerDz = (z + halfFootprint) - center.getZ();
            double radiusWithBlockRounding = radius + 0.71;
            if (centerDx * centerDx + centerDz * centerDz
                    > radiusWithBlockRounding * radiusWithBlockRounding) {
                continue;
            }

            found.add(new CampLocation(world.getUID(), x, z, site.groundY(), site.score(),
                    footprint, footprint));
        }

        return List.copyOf(found);
    }

    /**
     * Convenience overload using the plugin's configured spawn as the center.
     * Bingo4.getSpawn() must have been initialized before this is called.
     */
    public List<CampLocation> findCampLocations(int amount, int radius) {
        return findCampLocations(amount, radius, getConfiguredSpawn());
    }

    /**
     * Finds suitable locations and places camps, distributing types in
     * registration order. Returns only camps that were successfully pasted.
     */
    public List<CampInstance> createCamps(int amount, int radius, Location center) {
        assertMainThread();
        List<CampLocation> locations = findCampLocations(amount, radius, center);
        if (locations.isEmpty() || types.isEmpty()) return List.of();

        List<AbstractCampType> registeredTypes = new ArrayList<>(types.values());
        List<CampInstance> created = new ArrayList<>();
        CampRotation[] rotations = CampRotation.values();
        SplittableRandom random = new SplittableRandom(
                center.getWorld().getSeed()
                        ^ ((long) center.getBlockX() << 32)
                        ^ center.getBlockZ()
                        ^ System.nanoTime()
        );

        for (int i = 0; i < locations.size(); i++) {
            AbstractCampType type = registeredTypes.get(i % registeredTypes.size());
            CampRotation rotation = rotations[random.nextInt(rotations.length)];
            try {
                CampInstance camp = placeCamp(type, locations.get(i), center.getWorld(), rotation, random.nextLong());
                created.add(camp);
                for (SpawnPoint spawn : camp.spawnPoints()) {
                    Location pos = spawn.markerPosition();

                    Block block = plugin.getWorld().getBlockAt(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());

                    TextUtil.debugText("block:" + TextUtil.formatLocation(block.getLocation()));
                    block.setType(Material.AIR, false);
                }
            } catch (Exception exception) {
                plugin.getLogger().severe("Could not place camp type " + type.getId()
                        + " at " + locations.get(i) + ": " + exception.getMessage());
                plugin.getLogger().log(java.util.logging.Level.FINE, "Camp placement exception", exception);
            }
        }
        return List.copyOf(created);
    }

    /**
     * Creates camps of one specific registered type instead of distributing
     * the placements across all registered types.
     */
    public List<CampInstance> createCamps(
            int amount,
            int radius,
            Location center,
            String campTypeId
    ) {
        assertMainThread();
        AbstractCampType type = types.get(campTypeId);
        if (type == null) {
            throw new IllegalArgumentException("Unknown camp type: " + campTypeId);
        }

        List<CampLocation> locations = findCampLocations(amount, radius, center);
        List<CampInstance> created = new ArrayList<>();
        SplittableRandom random = new SplittableRandom(
                center.getWorld().getSeed()
                        ^ ((long) center.getBlockX() << 32)
                        ^ center.getBlockZ()
                        ^ System.nanoTime()
        );

        for (CampLocation location : locations) {
            CampRotation rotation = CampRotation.values()[
                    random.nextInt(CampRotation.values().length)];
            try {
                CampInstance camp = placeCamp(type, location, center.getWorld(),
                        rotation, random.nextLong());
                created.add(camp);
                for (SpawnPoint spawn : camp.spawnPoints()) {
                    Location pos = spawn.markerPosition();

                    Block block = plugin.getWorld().getBlockAt(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());

                    TextUtil.debugText("block:" + TextUtil.formatLocation(block.getLocation()));
                    block.setType(Material.AIR, false);
                }
            } catch (Exception exception) {
                plugin.getLogger().severe("Could not place camp type " + type.getId()
                        + " at " + location + ": " + exception.getMessage());
                plugin.getLogger().log(java.util.logging.Level.FINE,
                        "Camp placement exception", exception);
            }
        }
        return List.copyOf(created);
    }

    /** Convenience overload using Bingo4.getSpawn() as the center. */
    public List<CampInstance> createCamps(int amount, int radius) {
        return createCamps(amount, radius, getConfiguredSpawn());
    }

    private CampInstance placeCamp(
            AbstractCampType type,
            CampLocation location,
            World world,
            CampRotation rotation,
            long seed
    ) {
        if (!world.getUID().equals(location.worldId())) {
            throw new IllegalArgumentException("Camp location belongs to another world");
        }

        CampTemplate template = type.getTemplate();
        int pasteY = location.groundY() + 1 - template.groundOffsetY();

        // location.x()/z() is where the BASE's minimum corner must land. The base is
        // blended using its rotated size, and the structure is pasted so the rotated
        // base's minimum corner coincides with that point.
        int baseWidth = template.rotatedBaseWidth(rotation);
        int baseDepth = template.rotatedBaseDepth(rotation);
        int width = template.rotatedWidth(rotation);
        int depth = template.rotatedDepth(rotation);
        LocalPos rotatedBaseMin = template.rotatedBaseMin(rotation);
        int pasteX = location.x() - rotatedBaseMin.x();
        int pasteZ = location.z() - rotatedBaseMin.z();

        Site site = new Site(location.x(), location.z(), location.groundY(), location.terrainScore());
        terrainBlender.blend(world, site, blendRadius, width, depth);

        PreparedCamp prepared = template.prepare(rotation);
        prepared.paste(world, pasteX, pasteY, pasteZ, seed);

        List<SpawnPoint> spawnPoints = new ArrayList<>();
        List<CommandPanel> commandPanels = new ArrayList<>();

        for (CampMarker marker : prepared.markers()) {
            LocalPos local = marker.placedPosition();
            int x = pasteX + local.x();
            int y = pasteY + local.y();
            int z = pasteZ + local.z();
            Location position = new Location(world, x, y, z);

            if (marker.type() == MarkerType.SPAWN_POINT) {
                SpawnRole role = type.getSpawnRoles().getOrDefault(
                        marker.sourcePosition(), SpawnRole.UNASSIGNED);
                spawnPoints.add(new SpawnPoint(position, role));
            } else if (marker.type() == MarkerType.COMMAND_PANEL) {
                commandPanels.add(readCommandPanel(world, x, y, z));
            }
        }

        String instanceId = world.getUID() + ":" + UUID.randomUUID();
        // origin is the minimum corner of the rotated structure (the paste origin).
        Location origin = new Location(world, pasteX, pasteY, pasteZ);

        CampInstance camp = new CampInstance(
                instanceId, template.id(), rotation, origin, spawnPoints, commandPanels);

        registry.register(camp);
        campsByType.get(type.getId()).add(camp);
        type.onCampPlaced(camp);

        TextUtil.debugText("[Camp] Camp is located at " + TextUtil.formatLocation(origin));
        // Flood from the center of the base, not of the whole structure.
        Location start = new Location(world,
                location.x() + baseWidth / 2.0,
                location.groundY() + 1,
                location.z() + baseDepth / 2.0);

        TextUtil.debugText("[Camp] Flood is starting at " + TextUtil.formatLocation(start));

        FloodBlocks floodBlocks = new FloodBlocks(start, 2.0, type);
        floodBlocks.autoFlood(new Random());

        TextUtil.debugText("[Camp] rotation=" + rotation
                + " structure=" + template.width() + "x" + template.depth()
                + " base=" + template.baseWidth() + "x" + template.baseDepth()
                + " baseOffset=(" + template.baseOffsetX() + "," + template.baseOffsetZ() + ")"
                + " rotatedBaseMin=(" + rotatedBaseMin.x() + "," + rotatedBaseMin.z() + ")"
                + " pasteOrigin=(" + pasteX + "," + pasteZ + ")"
                + " baseCorner=(" + location.x() + "," + location.z() + ")");

        return camp;
    }

    private CommandPanel readCommandPanel(World world, int x, int y, int z) {
        Block block = world.getBlockAt(x, y, z);
        String command = "";
        if (block.getState() instanceof CommandBlock commandBlock) {
            command = commandBlock.getCommand();
        }

        BlockFace facing = BlockFace.SELF;
        if (block.getBlockData() instanceof Directional directional) {
            facing = directional.getFacing();
        }

        return new CommandPanel(
                new Location(world, x, y, z),
                block.getType(),
                facing,
                command
        );
    }

    private boolean isFarEnough(World world, int x, int z, int footprint, List<CampLocation> existing) {
        double minimumDistanceSquared = (double) minimumSpacing * minimumSpacing;
        double candidateCenterX = x + (footprint - 1) / 2.0;
        double candidateCenterZ = z + (footprint - 1) / 2.0;

        for (CampLocation location : existing) {
            double dx = candidateCenterX - location.centerX();
            double dz = candidateCenterZ - location.centerZ();
            if (dx * dx + dz * dz < minimumDistanceSquared) return false;
        }

        // Also avoid camps created by earlier calls to this manager.
        for (CampInstance camp : registry.all()) {
            Location origin = camp.origin();
            if (!origin.getWorld().getUID().equals(world.getUID())) continue;
            double[] campCenter = baseCenterOf(camp);
            double dx = candidateCenterX - campCenter[0];
            double dz = candidateCenterZ - campCenter[1];
            if (dx * dx + dz * dz < minimumDistanceSquared) return false;
        }
        return true;
    }

    /**
     * Center (in block-index space) of a placed camp's base. camp.origin() is the
     * minimum corner of the rotated structure, so the base center is found by adding
     * the rotated base offset. Falls back to a 16x16 base at the origin if the
     * template is no longer registered.
     */
    private double[] baseCenterOf(CampInstance camp) {
        Location origin = camp.origin();
        for (AbstractCampType type : types.values()) {
            CampTemplate template = type.getTemplate();
            if (!template.id().equals(camp.templateId())) continue;
            CampRotation rotation = camp.rotation();
            LocalPos baseMin = template.rotatedBaseMin(rotation);
            return new double[] {
                    origin.x() + baseMin.x() + (template.rotatedBaseWidth(rotation) - 1) / 2.0,
                    origin.z() + baseMin.z() + (template.rotatedBaseDepth(rotation) - 1) / 2.0
            };
        }
        return new double[] { origin.x() + 7.5, origin.z() + 7.5 };
    }

    private boolean chunksLoaded(World world, int minX, int minZ, int maxX, int maxZ) {
        int minChunkX = Math.floorDiv(minX, 16);
        int maxChunkX = Math.floorDiv(maxX, 16);
        int minChunkZ = Math.floorDiv(minZ, 16);
        int maxChunkZ = Math.floorDiv(maxZ, 16);

        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                if (!world.isChunkLoaded(chunkX, chunkZ)) return false;
            }
        }
        return true;
    }

    private Location getConfiguredSpawn() {
        try {
            return Bingo4.getSpawn();
        } catch (NullPointerException exception) {
            throw new IllegalStateException(
                    "Bingo4 spawn has not been configured yet", exception);
        }
    }

    private void assertMainThread() {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("CampManager must be used on the server thread");
        }
    }

    public CampRegistry getRegistry() {
        return registry;
    }

    public List<CampInstance> getCamps(String campTypeId) {
        List<CampInstance> camps = campsByType.get(campTypeId);
        return camps == null ? List.of() : List.copyOf(camps);
    }

    public List<CampInstance> getCamps(AbstractCampType campType) {
        Objects.requireNonNull(campType, "campType");
        return getCamps(campType.getId());
    }

    public Collection<CampInstance> getAllCamps() {
        return registry.all();
    }
}
