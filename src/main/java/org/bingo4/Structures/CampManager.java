package org.bingo4.Structures;

import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bingo4.Utility.TextUtil;
import org.bingo4.camps.core.Site;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

public class CampManager {

    private final Map<String, AbstractCampType> campTypes = new HashMap<>();
    private final List<CampInstance> camps = new ArrayList<>();

    private final TerrainAnalyzer terrainAnalyzer;

//    private final int structureHeight = 10000;
    private final int blendRadius = 10;
    private final int minimumSpacing = 40;
    private final int maxCandidateAttempts = 10000;

    public CampManager() {
        terrainAnalyzer = new TerrainAnalyzer(5, 2);
    }

    // --------------------------------------------------
    // Camp type registration
    // --------------------------------------------------

    public void registerCampType(AbstractCampType type) {
        Objects.requireNonNull(type);

        if (campTypes.containsKey(type.getId())) {
            throw new IllegalArgumentException(
                    "Camp type already registered: " + type.getId()
            );
        }

        campTypes.put(type.getId(), type);
    }

    public AbstractCampType getCampType(Structures id) {
        return campTypes.get(id.getId());
    }

    public AbstractCampType getCampType(String id) {
        return campTypes.get(id);
    }

    public Collection<AbstractCampType> getCampTypes() {
        return Collections.unmodifiableCollection(campTypes.values());
    }

    // --------------------------------------------------
    // Camp generation
    // --------------------------------------------------

    /**
     * Removes the first {@code amount} locations from {@code locations} and uses them to generate camps
     * @param locations list of locations
     * @param type tyoe if camp
     * @param amount amount to generate
     * @return list of camp instances
     */
    public List<CampInstance> generateCamps(List<Location> locations, AbstractCampType type, int amount) {
        int actualX = Math.min(amount, locations.size());

        List<Location> sub = locations.subList(0, actualX);
        List<Location> removedList = new ArrayList<>(sub);
        sub.clear();

        return generateCamps(removedList, type);
    }

    /**
     * Generates camps using the supplied types in round-robin order.
     *
     * For example, given:
     * [ForestCamp, BanditCamp, MilitaryCamp]
     *
     * The generated camps will use:
     * ForestCamp, BanditCamp, MilitaryCamp, ForestCamp, ...
     */
    public List<CampInstance> generateCamps(
            List<Location> locations,
            AbstractCampType type
    ) {

        Objects.requireNonNull(locations, "Locations cannot be null");
        Objects.requireNonNull(type, "Camp type cannot be null");

        TextUtil.debugText("Amount of Locations generated: " + locations.size());


        List<CampInstance> generated = new ArrayList<>();

        for (Location origin : locations) {
            if (origin == null || origin.getWorld() == null) {
                continue;
            }

            try {
                CampInstance instance = type.createInstance(origin, origin);

                type.place(instance);
                camps.add(instance);
                generated.add(instance);

                type.onPlaced(instance);
            } catch (Exception e) {
                TextUtil.debugText(String.format("[Camp Manager] Exception: %s ", e));
            }


        }

        return Collections.unmodifiableList(generated);
    }

    /**
     * Finds suitable locations for a particular camp type.
     * Does not place any structures or create CampInstances.
     */
    public List<Location> generateLocations(
            int amount,
            Location center,
            double radius,
            AbstractCampType type
    ) {
        Objects.requireNonNull(type, "Camp type cannot be null");

        if (amount <= 0) return List.of();

        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }

        if (center == null || center.getWorld() == null) {
            throw new IllegalArgumentException("A center with a world is required");
        }

        World world = center.getWorld();

        int width = type.getWidth();
        int depth = type.getDepth();

        if (width < 1 || depth < 1) {
            throw new IllegalArgumentException("Invalid camp dimensions");
        }

        double halfWidth = (width - 1) / 2.0;
        double halfDepth = (depth - 1) / 2.0;

        long seed = world.getSeed()
                ^ ((long) center.getBlockX() * 0x9E3779B97F4A7C15L)
                ^ ((long) center.getBlockZ() * 0xC2B2AE3D27D4EB4FL)
                ^ ((long) radius << 32)
                ^ amount;

        SplittableRandom random = new SplittableRandom(seed);

        List<Location> found = new ArrayList<>(amount);

        long desiredAttempts = Math.max(100L, (long) amount * 120L);
        int attempts = (int) Math.min(maxCandidateAttempts, desiredAttempts);

        for (int attempt = 0; attempt < attempts && found.size() < amount; attempt++) {

            // Uniform random distribution inside the circular radius.
            double distance = radius == 0
                    ? 0
                    : Math.sqrt(random.nextDouble()) * radius;

            double angle = random.nextDouble(0.0, Math.PI * 2.0);

            double targetX = center.getX() + Math.cos(angle) * distance;
            double targetZ = center.getZ() + Math.sin(angle) * distance;

            // Candidate represents the minimum X/Z corner of the footprint.
            int x = (int) Math.floor(targetX - halfWidth);
            int z = (int) Math.floor(targetZ - halfDepth);

            if (!isFarEnough(world, x, z, halfWidth, halfDepth, found)) {
                continue;
            }

//            if (!chunksLoaded(
//                    world,
//                    x - blendRadius,
//                    z - blendRadius,
//                    x + width - 1 + blendRadius,
//                    z + depth - 1 + blendRadius
//            )) {
//                continue;
//            }

            Optional<Site> evaluated = terrainAnalyzer.evaluate(
                    world,
                    x,
                    z,
                    type.getHeight(),
                    width,
                    depth
            );

            if (evaluated.isEmpty()) {
                continue;
            }

            Site site = evaluated.get();

            // Ensure the rounded candidate center remains within the radius.
            double dx = x + halfWidth - center.getX();
            double dz = z + halfDepth - center.getZ();

            double roundedRadius = radius + 0.71;

            if (dx * dx + dz * dz > roundedRadius * roundedRadius) {
                continue;
            }

            found.add(new Location(
                    world,
                    x,
                    site.groundY() + 1,
                    z
            ));
        }

        return found;
    }

    // --------------------------------------------------
    // Camp retrieval
    // --------------------------------------------------

    public List<CampInstance> getCamps() {
        return Collections.unmodifiableList(camps);
    }

    public List<CampInstance> getCamps(String typeId) {
        return camps.stream()
                .filter(camp -> camp.getTypeId().equals(typeId))
                .toList();
    }

    public Optional<CampInstance> getNearestCamp(Location location) {
        return camps.stream()
                .filter(camp -> camp.getCenter().getWorld().equals(location.getWorld()))
                .min(Comparator.comparingDouble(
                        camp -> camp.getCenter().distanceSquared(location)
                ));
    }

    public void removeCamp(CampInstance instance) {
        camps.remove(instance);
    }

    public void clearCamps() {
        camps.clear();
    }



    private boolean isFarEnough(
            World world,
            int x,
            int z,
            double halfWidth,
            double halfDepth,
            List<Location> found
    ) {
        double minimumDistanceSquared =
                (double) minimumSpacing * minimumSpacing;

        double candidateCenterX = x + halfWidth;
        double candidateCenterZ = z + halfDepth;

        // Check locations generated during this search.
        for (Location existing : found) {
            double dx = candidateCenterX - (existing.getBlockX() + halfWidth);
            double dz = candidateCenterZ - (existing.getBlockZ() + halfDepth);

            if (dx * dx + dz * dz < minimumDistanceSquared) {
                return false;
            }
        }

        // Check camps already registered in the manager.
        for (CampInstance camp : camps) {
            Location existing = camp.getCenter();

            if (existing.getWorld() == null ||
                    !existing.getWorld().getUID().equals(world.getUID())) {
                continue;
            }

            double dx = candidateCenterX - existing.getX();
            double dz = candidateCenterZ - existing.getZ();

            if (dx * dx + dz * dz < minimumDistanceSquared) {
                return false;
            }
        }

        return true;
    }

    private boolean chunksLoaded(
            World world,
            int minX,
            int minZ,
            int maxX,
            int maxZ
    ) {
        int minChunkX = Math.floorDiv(minX, 16);
        int maxChunkX = Math.floorDiv(maxX, 16);

        int minChunkZ = Math.floorDiv(minZ, 16);
        int maxChunkZ = Math.floorDiv(maxZ, 16);

        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                if (!world.isChunkLoaded(chunkX, chunkZ)) {
                    return false;
                }
            }
        }

        return true;
    }



}
