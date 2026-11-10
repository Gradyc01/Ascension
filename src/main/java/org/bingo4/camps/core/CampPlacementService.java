package org.bingo4.camps.core;

import org.bingo4.Structures.TerrainAnalyzer;
import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public final class CampPlacementService {
    private final Plugin plugin;
    private final List<CampTemplate> templates;
    private final CampPlanner planner;
    private final TerrainAnalyzer terrainAnalyzer;
    private final TerrainBlender terrainBlender;
    private final SpawnRoleConfig spawnRoleConfig;
    private final CampStore store;
    private final CampRegistry registry;
    private final Set<String> processedRegions;
    private final int regionSize;
    private final int searchRadius;
    private final int attempts;
    private final int activationRadius;
    private final int blendRadius;
    private final boolean enabled;
    private final Set<String> worldWhitelist;

    public CampPlacementService(
            Plugin plugin,
            List<CampTemplate> templates,
            CampPlanner planner,
            TerrainAnalyzer terrainAnalyzer,
            TerrainBlender terrainBlender,
            SpawnRoleConfig spawnRoleConfig,
            CampStore store,
            CampRegistry registry,
            int regionSize,
            int searchRadius,
            int attempts,
            int activationRadius,
            int blendRadius,
            boolean enabled,
            Set<String> worldWhitelist
    ) {
        this.plugin = plugin;
        this.templates = List.copyOf(templates);
        this.planner = planner;
        this.terrainAnalyzer = terrainAnalyzer;
        this.terrainBlender = terrainBlender;
        this.spawnRoleConfig = spawnRoleConfig;
        this.store = store;
        this.registry = registry;
        this.processedRegions = store.loadProcessedRegions();
        for (CampInstance camp : store.loadCamps()) registry.register(camp);
        this.regionSize = regionSize;
        this.searchRadius = searchRadius;
        this.attempts = attempts;
        this.activationRadius = activationRadius;
        this.blendRadius = blendRadius;
        this.enabled = enabled;
        this.worldWhitelist = Set.copyOf(worldWhitelist);
    }

    /** Called on chunk-boundary movement; all Bukkit world access stays on the server thread. */
    public void onPlayerChangedChunk(Player player) {
        if (!enabled || templates.isEmpty()) return;
        World world = player.getWorld();
        if (!worldWhitelist.isEmpty() && !worldWhitelist.contains(world.getName())) return;

        int regionX = Math.floorDiv(player.getLocation().getBlockX(), regionSize);
        int regionZ = Math.floorDiv(player.getLocation().getBlockZ(), regionSize);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                tryProcessRegion(world, regionX + dx, regionZ + dz, player.getLocation().getX(),
                        player.getLocation().getZ());
            }
        }
    }

    private void tryProcessRegion(World world, int regionX, int regionZ, double playerX, double playerZ) {
        String key = regionKey(world.getUID(), regionX, regionZ);
        if (processedRegions.contains(key)) return;

        Candidate candidate = planner.candidate(world.getSeed(), regionX, regionZ);
        if (!candidate.shouldSpawn()) {
            processedRegions.add(key);
            saveState();
            return;
        }

        double dx = playerX - candidate.x();
        double dz = playerZ - candidate.z();
        if (dx * dx + dz * dz > (double) activationRadius * activationRadius) return;

        SplittableRandom random = new SplittableRandom(candidate.seed() ^ 0xD1B54A32D192ED03L);
        CampTemplate template = templates.get(random.nextInt(templates.size()));

        CampRotation rotation = candidate.rotation();
        int baseWidth = template.rotatedBaseWidth(rotation);
        int baseDepth = template.rotatedBaseDepth(rotation);

        // The structure can overhang the base, so load enough chunks for both it and the blend ring.
        int margin = Math.max(blendRadius, template.structureMargin());
        int minX = candidate.x() - searchRadius - margin;
        int maxX = candidate.x() + searchRadius + baseWidth - 1 + margin;
        int minZ = candidate.z() - searchRadius - margin;
        int maxZ = candidate.z() + searchRadius + baseDepth - 1 + margin;
        if (!areChunksLoaded(world, minX, minZ, maxX, maxZ)) return;

        Optional<Site> best = findBestSite(world, candidate, template.height(),
                baseWidth, baseDepth, random);
        if (best.isEmpty()) {
            processedRegions.add(key);
            saveState();
            return;
        }

        Site site = best.get();
        int pasteY = site.groundY() + 1 - template.groundOffsetY();

        // site.x()/z() is where the base's minimum corner lands; shift the paste origin
        // so the rotated base (not the whole structure) sits on the selected terrain.
        LocalPos rotatedBaseMin = template.rotatedBaseMin(rotation);
        int pasteX = site.x() - rotatedBaseMin.x();
        int pasteZ = site.z() - rotatedBaseMin.z();
        try {
            terrainBlender.blend(world, site, blendRadius, baseWidth, baseDepth);
            PreparedCamp prepared = template.prepare(rotation);
            prepared.paste(world, pasteX, pasteY, pasteZ, candidate.seed());
            CampInstance camp = createCampInstance(world, pasteX, pasteY, pasteZ, template,
                    rotation, prepared.markers(), spawnRoleConfig.rolesFor(template.id()),
                    regionX, regionZ);
            registry.register(camp);
            processedRegions.add(key);
            saveState();
            plugin.getLogger().info("Placed " + template.id() + " at " + world.getName()
                    + " " + pasteX + "," + pasteY + "," + pasteZ);
        } catch (Exception exception) {
            // Avoid repeatedly flattening/pasting the same failed location on every chunk crossing.
            processedRegions.add(key);
            saveState();
            plugin.getLogger().log(Level.SEVERE, "Failed to place camp for region " + key, exception);
        }
    }

    private Optional<Site> findBestSite(World world, Candidate candidate, int structureHeight,
                                        int baseWidth, int baseDepth, SplittableRandom random) {
        Site best = null;
        for (int i = 0; i < attempts; i++) {
            int offsetX = random.nextInt(-searchRadius, searchRadius + 1);
            int offsetZ = random.nextInt(-searchRadius, searchRadius + 1);
            Optional<Site> result = terrainAnalyzer.evaluate(world,
                    candidate.x() + offsetX, candidate.z() + offsetZ, structureHeight,
                    baseWidth, baseDepth);
            if (result.isPresent() && (best == null || result.get().score() < best.score())) {
                best = result.get();
            }
        }
        return Optional.ofNullable(best);
    }

    private CampInstance createCampInstance(
            World world, int pasteX, int pasteY, int pasteZ, CampTemplate template, CampRotation rotation,
            List<CampMarker> markers, Map<LocalPos, SpawnRole> roles, int regionX, int regionZ) {
        List<SpawnPoint> spawnPoints = new ArrayList<>();
        List<CommandPanel> commandPanels = new ArrayList<>();

        for (CampMarker marker : markers) {
            LocalPos local = marker.placedPosition();
            int x = pasteX + local.x();
            int y = pasteY + local.y();
            int z = pasteZ + local.z();
            Location position = new Location(world, x, y, z);

            if (marker.type() == MarkerType.SPAWN_POINT) {
                SpawnRole role = roles.getOrDefault(marker.sourcePosition(), SpawnRole.UNASSIGNED);
                spawnPoints.add(new SpawnPoint(position, role));
            } else {
                commandPanels.add(readCommandPanel(world, x, y, z));
            }
        }

        String instanceId = world.getUID() + ":" + regionX + ":" + regionZ;
        // origin is the minimum corner of the rotated structure (the paste origin).
        Location origin = new Location(world, pasteX, pasteY, pasteZ);
        return new CampInstance(instanceId, template.id(), rotation, origin,
                spawnPoints, commandPanels);
    }

    private CommandPanel readCommandPanel(World world, int x, int y, int z) {
        Block block = world.getBlockAt(x, y, z);
        Material material = block.getType();
        String command = "";
        if (block.getState() instanceof CommandBlock commandBlock) {
            command = commandBlock.getCommand();
        }
        BlockFace facing = BlockFace.SELF;
        if (block.getBlockData() instanceof Directional directional) {
            facing = directional.getFacing();
        }
        return new CommandPanel(new Location(world, x, y, z),
                material, facing, command);
    }

    private boolean areChunksLoaded(World world, int minX, int minZ, int maxX, int maxZ) {
        int minChunkX = Math.floorDiv(minX, 16);
        int maxChunkX = Math.floorDiv(maxX, 16);
        int minChunkZ = Math.floorDiv(minZ, 16);
        int maxChunkZ = Math.floorDiv(maxZ, 16);
        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                if (!world.isChunkLoaded(cx, cz)) return false;
            }
        }
        return true;
    }

    private String regionKey(UUID worldId, int regionX, int regionZ) {
        return worldId + ":" + regionX + ":" + regionZ;
    }

    private void saveState() {
        try {
            store.save(processedRegions, registry.all());
        } catch (IOException exception) {
            plugin.getLogger().log(Level.SEVERE, "Could not persist camp state", exception);
        }
    }

    public CampRegistry registry() {
        return registry;
    }
}
