package me.depickcator.ascension.General.LocationChecker;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BiomeSearchResult;
import org.bukkit.util.StructureSearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class LocationCheck {
    private final Location spawn;
    private final World world;
    private final HashMap<Structure, LocationStorage> structures;
    private final HashMap<Biome, LocationStorage> biomes;
    private Pair<Integer, Integer> locationScore;
    private final int worldBorderSize;
    private boolean checkCompleted;
    private BukkitTask task;
    public LocationCheck(Location spawn) {
        this.spawn = spawn;
        this.world = spawn.getWorld();
        structures = new HashMap<>();
        biomes = new HashMap<>();
        locationScore = new MutablePair<>(0, 0);
        worldBorderSize = Ascension.getInstance().getSettingsUI().getSettings().getWorldBorderSize();
        checkCompleted = false;
        runLocations();
    }

    public void cancelCheck() {
        task.cancel();
        task = null;
    }

    public boolean isALocation(Biome... biome) {
        for (Biome b : biome) {
            if (biomes.get(b) != null && biomes.get(b).isInWorld()) {
                return true;
            }
        }
        return false;
    }

    public boolean isALocation(Structure... structure) {
        for (Structure s : structure) {
            if (structures.get(s) != null && structures.get(s).isInWorld()) {
                return true;
            }
        }
        return false;
    }

    public int getPercentageScore() {
        return (locationScore.getLeft() * 100 / locationScore.getRight());
    }

    private List<LocationStorage> initBiomes() {
        List<LocationStorage> storages = new ArrayList<>(List.of(
                new LocationStorage(Biome.SWAMP, 10, "Swamp"),
                new LocationStorage(Biome.JUNGLE, 10, "Jungle"),
                new LocationStorage(Biome.DESERT, 10, "Desert"),
                new LocationStorage(Biome.TAIGA, 10, "Taiga"),
                new LocationStorage(Biome.DARK_FOREST, 10, "Dark forest"),

                new LocationStorage(Biome.CHERRY_GROVE, 7, "Cherry Grove"),
                new LocationStorage(Biome.LUSH_CAVES, 7, "Lush Caves"),
                new LocationStorage(Biome.WARM_OCEAN, 7, "Warm ocean"),
                new LocationStorage(Biome.BAMBOO_JUNGLE, 7, "Bamboo jungle"),
                new LocationStorage(Biome.DEEP_DARK, 7, "Deep dark"),
                new LocationStorage(Biome.LUSH_CAVES, 7, "Lush caves"),

                new LocationStorage(Biome.MUSHROOM_FIELDS, 3, "Mushroom fields"),
                new LocationStorage(Biome.MANGROVE_SWAMP, 5, "Mangrove swamp"),
                new LocationStorage(Biome.PALE_GARDEN, 5, "Pale garden")
        ));
        return storages;
    }

    private List<LocationStorage> initStructures() {
        List<LocationStorage> storages = new ArrayList<>(List.of(
                new LocationStorage(Structure.OCEAN_RUIN_COLD, 10, "Ocean Ruin"),
                new LocationStorage(Structure.SHIPWRECK, 10, "Ship Wreck"),
                new LocationStorage(Structure.VILLAGE_DESERT, 10, "Village: Desert"),
                new LocationStorage(Structure.VILLAGE_PLAINS, 10, "Village: Plains"),
                new LocationStorage(Structure.VILLAGE_SAVANNA, 10, "Village: Savanna"),
                new LocationStorage(Structure.VILLAGE_SNOWY, 10, "Village: Snowy"),
                new LocationStorage(Structure.VILLAGE_TAIGA, 10, "Village: Taiga"),

                new LocationStorage(Structure.PILLAGER_OUTPOST, 7, "Pillager Outpost"),
                new LocationStorage(Structure.MONUMENT, 7, "Ocean Monument"),
                new LocationStorage(Structure.TRIAL_CHAMBERS, 7, "Trial Chambers"),

                new LocationStorage(Structure.DESERT_PYRAMID, 5, "Desert Pyramid"),
                new LocationStorage(Structure.MANSION, 3, "Mansion"),
                new LocationStorage(Structure.ANCIENT_CITY, 5, "Ancient City")
        ));
        return storages;
    }

    private void runLocations() {
        List<LocationStorage> allChecks = new ArrayList<>(initStructures());
        allChecks.addAll(initBiomes());
        task = new BukkitRunnable() {
            @Override
            public void run() {
                //structures.isEmpty() && biomes.isEmpty()
                if (allChecks.isEmpty()) {
                    cancel();
                    checkCompleted = true;
                    TextUtil.debugText("World Checker Complete");
                    return;
                }
                LocationStorage check = allChecks.getFirst();
                boolean found = false;
                if (check.getStructure() != null) {
                    found = checkLocation(check.getStructure(), check);
                } else if (check.getBiome() != null) {
                    found = checkLocation(check.getBiome(), check);
                }
                TextUtil.debugText(check.getDisplayName() + "         " + found);
                allChecks.removeFirst();


            }
        }.runTaskTimer(Ascension.getInstance(), 0, 20);

    }

    private boolean checkLocation(Structure structure, LocationStorage storage) {
        StructureSearchResult result = world.locateNearestStructure(spawn, structure, worldBorderSize, false);
        boolean ans;
        if (result == null) {
            ans = false;
        } else {
            ans = calculateEuclideanDistance(result.getLocation(), spawn) <= worldBorderSize;
        }
        storage.setInWorld(ans);
        structures.put(structure, storage);
        addScore(ans, storage.getPoints());
        return ans;
    }

    private boolean checkLocation(Biome biome, LocationStorage storage) {
        BiomeSearchResult result = world.locateNearestBiome(spawn, worldBorderSize, biome);
        boolean ans = result != null;
        storage.setInWorld(ans);
        biomes.put(biome, storage);
        addScore(ans, storage.getPoints());
        return ans;
    }


    private void addScore(boolean success, int total) {
        int score = success ? locationScore.getLeft() + total : locationScore.getLeft();
        locationScore = new MutablePair<>(score , locationScore.getRight() + total);
    }

    private double calculateEuclideanDistance(Location loc1, Location loc2) {
         return Math.sqrt(Math.pow(loc1.getX() - loc2.getX(), 2)
                 + Math.pow(loc1.getY() - loc2.getY(), 2)
                 + Math.pow(loc1.getZ() - loc2.getZ(), 2));
    }

    public Component printLocations() {
        Component text = TextUtil.makeText("");
        Collection<LocationStorage> storages = new ArrayList<>(structures.values());
        storages.addAll(biomes.values());
        for (LocationStorage storage : storages) {
            Component boolText = storage.isInWorld() ?
                    TextUtil.makeText("TRUE", TextUtil.GREEN) :
                    TextUtil.makeText("FALSE", TextUtil.RED);
            text = text.append(TextUtil.makeText("\n" + storage.getDisplayName() + "             ", TextUtil.GOLD).append(boolText));
        }
        return text;

    }

    public boolean isCheckCompleted() {
        return checkCompleted;
    }
}
