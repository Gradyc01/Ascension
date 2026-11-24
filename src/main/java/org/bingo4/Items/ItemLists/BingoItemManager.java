package org.bingo4.Items.ItemLists;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.GeneratedStructure;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BingoItemManager {

    private static final String GITHUB_URL =
            "https://raw.githubusercontent.com/Gradyc01/AscensionWorlds/main/bingo_lists/default.json";

    private static final Gson GSON = new Gson();

    private final Map<String, ParsedCategory> categories;

    /** null = no scan loaded, so nothing is filtered out. */
    private volatile WorldScan scan;

    private BingoItemManager(Map<String, ParsedCategory> categories, WorldScan scan) {
        this.categories = Collections.unmodifiableMap(new LinkedHashMap<>(categories));
        this.scan = scan;
    }

    // ------------------------------------------------------------
    // JSON MODEL
    // ------------------------------------------------------------

    public static final class Category {
        public String desc;
        public List<Entry> items;
    }

    public static final class Entry {

        @SerializedName("item_names")
        public List<ItemDef> itemNames;

        @SerializedName("choosing")
        public int choosing = 1;
    }

    /** One item. It has found_in_biomes OR found_in_structures. */
    public static final class ItemDef {

        @SerializedName("name")
        public String name;

        @SerializedName("found_in_biomes")
        public List<String> foundInBiomes;

        @SerializedName("found_in_structures")
        public List<String> foundInStructures;
    }

    // ------------------------------------------------------------
    // WORLD SCAN  (<world>/scan.json)
    // ------------------------------------------------------------

    /**
     * The biomes and structures that exist in the overworld we are playing on.
     * Holds plain strings ("minecraft:plains"), no registry access, so it is
     * safe to load asynchronously.
     *
     * Nether / End biomes and structures are not in the scan. They are always
     * treated as available for now.
     */
    public static final class WorldScan {

        private static final Set<String> NON_OVERWORLD_BIOMES = Set.of(
                "minecraft:nether_wastes", "minecraft:soul_sand_valley",
                "minecraft:crimson_forest", "minecraft:warped_forest",
                "minecraft:basalt_deltas", "minecraft:the_end",
                "minecraft:end_highlands", "minecraft:end_midlands",
                "minecraft:small_end_islands", "minecraft:end_barrens"
        );

        private static final Set<String> NON_OVERWORLD_STRUCTURES = Set.of(
                "minecraft:fortress", "minecraft:bastion_remnant",
                "minecraft:nether_fossil", "minecraft:ruined_portal_nether",
                "minecraft:end_city"
        );

        private final Set<String> biomes;
        private final Set<String> structures;
        private final boolean complete;

        private WorldScan(Set<String> biomes, Set<String> structures, boolean complete) {
            this.biomes = biomes;
            this.structures = structures;
            this.complete = complete;
        }

        public boolean isComplete() { return complete; }

        public boolean hasBiome(Biome biome) {
            NamespacedKey key = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.BIOME)
                    .getKey(biome);
            if (key == null) return false; // biome without a registry key
            String id = key.toString();
            return NON_OVERWORLD_BIOMES.contains(id) || biomes.contains(id);
        }

        public boolean hasStructure(Structure structure) {
            NamespacedKey key = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.STRUCTURE)
                    .getKey(structure);
            if (key == null) return false; // structures can exist without a key
            String id = key.toString();
            return NON_OVERWORLD_STRUCTURES.contains(id) || structures.contains(id);
        }
    }

    /** Reads scan.json. Pass e.g. plugin.getWorld().getWorldPath() + "/scan.json". */
    public static WorldScan loadScan(String scanFile) {
        return loadScan(Path.of(scanFile));
    }

    /** Returns null (and logs a warning) if the file is missing or unreadable. */
    public static WorldScan loadScan(Path scanFile) {
        if (!Files.isRegularFile(scanFile)) {
            TextUtil.debugText("No world scan found at " + scanFile + " - items will not be filtered by world contents.", Level.WARNING);
            return null;
        }
        try {
            JsonObject root = JsonParser.parseString(Files.readString(scanFile)).getAsJsonObject();

            Set<String> biomes = new HashSet<>();
            if (root.has("biomes")) biomes.addAll(root.getAsJsonObject("biomes").keySet());

            Set<String> structures = new HashSet<>();
            if (root.has("structures")) structures.addAll(root.getAsJsonObject("structures").keySet());

            boolean complete = root.has("complete") && root.get("complete").getAsBoolean();
            if (!complete) TextUtil.debugText("World scan is marked incomplete, some obtainable items may be filtered out.", Level.WARNING);

            TextUtil.debugText("Loaded world scan: " + biomes.size() + " biomes, " + structures.size() + " structures.", Level.INFO);
            return new WorldScan(Set.copyOf(biomes), Set.copyOf(structures), complete);
        } catch (IOException | RuntimeException ex) {
            TextUtil.debugText(String.format("Could not read world scan %s: %s", scanFile, ex), Level.WARNING);
            return null;
        }
    }

    /** Swap in a scan after the manager has been created. */
    public void setWorldScan(WorldScan scan) {
        this.scan = scan;
    }

    // ------------------------------------------------------------
    // PARSED MODEL
    // ------------------------------------------------------------

    public record ParsedItem(
            Material material,
            Set<Biome> biomes,
            Set<Structure> structures
    ) {

        public boolean isBiomeBased() {
            return !biomes.isEmpty();
        }

        public boolean isStructureBased() {
            return !structures.isEmpty();
        }

        /** True if this world has a biome or structure that can give this item. */
        public boolean isObtainable(WorldScan scan) {
            if (scan == null) return true;
            for (Biome b : biomes) if (scan.hasBiome(b)) return true;
            for (Structure s : structures) if (scan.hasStructure(s)) return true;
            return false;
        }

        public boolean isAt(Location location) {
            return isBiomeBased()
                    ? isInBiome(location, biomes)
                    : isInStructure(location, structures);
        }
    }

    public record ParsedEntry(
            List<ParsedItem> items,
            int choosing
    ) {

        public List<Material> materials() {
            return items.stream().map(ParsedItem::material).toList();
        }

        /** The items of this entry that this world can actually provide. */
        public List<ParsedItem> obtainable(WorldScan scan) {
            List<ParsedItem> result = new ArrayList<>();
            for (ParsedItem item : items) {
                if (item.isObtainable(scan)) result.add(item);
            }
            return result;
        }

        public List<Material> rollMaterials(Random random, WorldScan scan) {
            List<ParsedItem> copy = obtainable(scan);
            Collections.shuffle(copy, random);

            List<Material> rolled = new ArrayList<>();
            for (ParsedItem item : copy.subList(0, Math.min(choosing, copy.size()))) {
                rolled.add(item.material());
            }
            return rolled;
        }
    }

    public record ParsedCategory(
            String name,
            String desc,
            List<ParsedEntry> entries
    ) {}

    // ------------------------------------------------------------
    // DOWNLOAD
    // ------------------------------------------------------------

    /**
     * Downloads the JSON from GitHub.
     *
     * This method does not access Bukkit/Paper registries,
     * so it is safe to call asynchronously.
     */
    public static String downloadJson()
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GITHUB_URL))
                .timeout(Duration.ofSeconds(30))
                .header("User-Agent", "Bingo4")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "GitHub returned HTTP " + response.statusCode()
            );
        }
        TextUtil.debugText(response.body());
        return response.body();
    }

    // ------------------------------------------------------------
    // PARSING
    // ------------------------------------------------------------

    /** Parses without a world scan (nothing is filtered). */
    public static BingoItemManager parse(String json, Logger log) {
        return parse(json, null, log);
    }

    /**
     * Parses the downloaded JSON.
     *
     * IMPORTANT: Call this on the server's main thread because
     * it accesses Paper's registries.
     *
     * @param scan result of loadScan(...), or null to skip world filtering
     */
    public static BingoItemManager parse(String json, WorldScan scan, Logger log) {

        Map<String, Category> raw = GSON.fromJson(
                json,
                new TypeToken<LinkedHashMap<String, Category>>() {}.getType()
        );

        if (raw == null || raw.isEmpty()) {
            throw new IllegalArgumentException("Bingo JSON contains no categories!");
        }

        Map<String, ParsedCategory> result = new LinkedHashMap<>();

        for (Map.Entry<String, Category> categoryEntry : raw.entrySet()) {

            String categoryName = categoryEntry.getKey();
            Category category = categoryEntry.getValue();

            List<ParsedEntry> parsedEntries = new ArrayList<>();

            if (category.items == null) {
                log.warning("[" + categoryName + "] Category has no items.");
                continue;
            }

            for (Entry entry : category.items) {
                ParsedEntry parsed = parseEntry(entry, categoryName, log);
                if (parsed != null) {
                    parsedEntries.add(parsed);
                }
            }

            result.put(
                    categoryName,
                    new ParsedCategory(categoryName, category.desc, List.copyOf(parsedEntries))
            );

            log.info("Loaded " + parsedEntries.size() + " entries from category " + categoryName);
        }

        BingoItemManager manager = new BingoItemManager(result, scan);

        if (scan != null) {
            for (ParsedCategory category : manager.categories.values()) {
                for (ParsedEntry entry : category.entries()) {
                    if (entry.obtainable(scan).isEmpty()) {
                        log.warning("[" + category.name() + "] Nothing in this entry is obtainable in this world: "
                                + entry.materials());
                    }
                }
            }
        }

        return manager;
    }

    private static ParsedEntry parseEntry(Entry entry, String category, Logger log) {

        if (entry.itemNames == null || entry.itemNames.isEmpty()) {
            log.warning("[" + category + "] Entry has no item_names.");
            return null;
        }

        List<ParsedItem> items = new ArrayList<>();

        for (ItemDef def : entry.itemNames) {

            if (def == null || def.name == null) {
                throw new RuntimeException(String.format("[%s] Item without a name", category));
            }

            Material material = parseMaterial(def.name);

            if (material == null) {
                throw new RuntimeException(String.format("[%s] Unknown material: %s", category, def.name));
            }

            Set<Biome> biomes = new LinkedHashSet<>();

            if (def.foundInBiomes != null) {
                for (String id : def.foundInBiomes) {
                    Biome biome = parseBiome(id);
                    if (biome == null) {
                        throw new RuntimeException(String.format("[%s] %s: unknown biome: %s", category, def.name, id));
                    }
                    biomes.add(biome);
                }
            }

            Set<Structure> structures = new LinkedHashSet<>();

            if (def.foundInStructures != null) {
                for (String id : def.foundInStructures) {
                    Structure structure = parseStructure(id);
                    if (structure == null) {
                        throw new RuntimeException(String.format("[%s] %s: unknown structure: %s", category, def.name, id));
                    }
                    structures.add(structure);
                }
            }

            if (biomes.isEmpty() && structures.isEmpty()) {
                throw new RuntimeException(String.format("[%s] %s has no biomes or structures", category, def.name));
            }

            items.add(new ParsedItem(
                    material,
                    Collections.unmodifiableSet(biomes),
                    Collections.unmodifiableSet(structures)
            ));
        }

        int choosing = Math.max(1, Math.min(entry.choosing, items.size()));

        return new ParsedEntry(List.copyOf(items), choosing);
    }

    // ------------------------------------------------------------
    // MATERIAL / BIOME / STRUCTURE PARSING
    // ------------------------------------------------------------

    public static Material parseMaterial(String name) {
        return Material.matchMaterial(name);
    }

    public static Biome parseBiome(String id) {

        NamespacedKey key = NamespacedKey.fromString(id.toLowerCase(Locale.ROOT));

        if (key == null) {
            return null;
        }

        Registry<Biome> registry =
                RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME);

        return registry.get(key);
    }

    public static Structure parseStructure(String id) {

        NamespacedKey key = NamespacedKey.fromString(id.toLowerCase(Locale.ROOT));

        if (key == null) {
            return null;
        }

        Registry<Structure> registry =
                RegistryAccess.registryAccess().getRegistry(RegistryKey.STRUCTURE);

        return registry.get(key);
    }

    // ------------------------------------------------------------
    // ITEM SELECTION
    // ------------------------------------------------------------

    /**
     * Gets a specified number of unique random materials from a category.
     *
     * Each JSON entry is considered once. Its "choosing" property
     * determines how many materials can be selected from that entry.
     * Only items that the scanned world can provide (a biome or structure
     * from the scan, or any nether/end one) are ever picked.
     *
     * @param category Category name, e.g. "Common"
     * @param amount Number of materials to retrieve
     */
    public List<Material> getMaterials(String category, int amount) {

        if (amount <= 0) {
            return Collections.emptyList();
        }

        ParsedCategory parsedCategory = getCategory(category);

        if (parsedCategory == null) {
            throw new IllegalArgumentException("Unknown bingo category: " + category);
        }

        WorldScan currentScan = this.scan;

        List<ParsedEntry> entries = new ArrayList<>(parsedCategory.entries());
        Collections.shuffle(entries);

        Set<Material> selected = new LinkedHashSet<>();

        for (ParsedEntry entry : entries) {

            List<ParsedItem> candidates = entry.obtainable(currentScan);

            if (candidates.isEmpty()) {
                continue;
            }

            Collections.shuffle(candidates);

            int choosing = Math.min(entry.choosing(), candidates.size());

            for (int i = 0; i < choosing; i++) {

                selected.add(candidates.get(i).material());

                if (selected.size() >= amount) {
                    return new ArrayList<>(selected);
                }
            }
        }

        throw new IllegalArgumentException(
                "Requested " + amount + " items from category " + category
                        + ", but only " + selected.size()
                        + " obtainable unique materials exist in this world."
        );
    }

    public List<ItemStack> getItems(String category, int amount) {
        List<ItemStack> items = new ArrayList<>();
        for (Material material : getMaterials(category, amount)) {
            items.add(new ItemStack(material, 1));
        }
        return items;
    }

    // ------------------------------------------------------------
    // CATEGORY ACCESS
    // ------------------------------------------------------------

    public ParsedCategory getCategory(String name) {
        return categories.get(name);
    }

    public Set<String> getCategories() {
        return Collections.unmodifiableSet(categories.keySet());
    }

    public Map<String, ParsedCategory> getAllCategories() {
        return categories;
    }

    // ------------------------------------------------------------
    // LOCATION CHECKS
    // ------------------------------------------------------------

    public static boolean isInBiome(Location location, Set<Biome> biomes) {

        Biome current = location.getWorld().getBiome(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        return biomes.contains(current);
    }

    public static boolean isInStructure(Location location, Set<Structure> structures) {

        Chunk chunk = location.getChunk();

        for (Structure structure : structures) {
            for (GeneratedStructure generated : chunk.getStructures(structure)) {
                if (generated.getBoundingBox().contains(location.toVector())) {
                    return true;
                }
            }
        }

        return false;
    }
}