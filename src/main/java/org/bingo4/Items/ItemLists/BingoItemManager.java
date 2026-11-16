package org.bingo4.Items.ItemLists;

import com.google.gson.Gson;
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
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

public final class BingoItemManager {

    private static final String GITHUB_URL =
            "https://raw.githubusercontent.com/Gradyc01/AscensionWorlds/main/bingo_lists/default.json";

    private static final Gson GSON = new Gson();

    private final Map<String, ParsedCategory> categories;

    private BingoItemManager(Map<String, ParsedCategory> categories) {
        this.categories = Collections.unmodifiableMap(
                new LinkedHashMap<>(categories)
        );
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
        public List<String> itemNames;

        @SerializedName("choosing")
        public int choosing = 1;

        @SerializedName("found_in_biomes")
        public List<String> foundInBiomes;

        @SerializedName("found_in_structures")
        public List<String> foundInStructures;
    }

    // ------------------------------------------------------------
    // PARSED MODEL
    // ------------------------------------------------------------

    public record ParsedEntry(
            List<Material> materials,
            int choosing,
            Set<Biome> biomes,
            Set<Structure> structures
    ) {

        public boolean isBiomeBased() {
            return !biomes.isEmpty();
        }

        public boolean isStructureBased() {
            return !structures.isEmpty();
        }

        public List<Material> rollMaterials(Random random) {
            List<Material> copy = new ArrayList<>(materials);
            Collections.shuffle(copy, random);

            return new ArrayList<>(
                    copy.subList(0, Math.min(choosing, copy.size()))
            );
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

    /**
     * Parses the downloaded JSON.
     *
     * IMPORTANT: Call this on the server's main thread because
     * it accesses Paper's registries.
     */
    public static BingoItemManager parse(String json, Logger log) {

        Map<String, Category> raw = GSON.fromJson(
                json,
                new TypeToken<LinkedHashMap<String, Category>>() {}.getType()
        );

        if (raw == null || raw.isEmpty()) {
            throw new IllegalArgumentException(
                    "Bingo JSON contains no categories!"
            );
        }

        Map<String, ParsedCategory> result = new LinkedHashMap<>();

        for (Map.Entry<String, Category> categoryEntry : raw.entrySet()) {

            String categoryName = categoryEntry.getKey();
            Category category = categoryEntry.getValue();

            List<ParsedEntry> parsedEntries = new ArrayList<>();

            if (category.items == null) {
                log.warning(
                        "[" + categoryName + "] Category has no items."
                );
                continue;
            }

            for (Entry entry : category.items) {

                ParsedEntry parsed = parseEntry(
                        entry,
                        categoryName,
                        log
                );

                if (parsed != null) {
                    parsedEntries.add(parsed);
                }
            }

            result.put(
                    categoryName,
                    new ParsedCategory(
                            categoryName,
                            category.desc,
                            List.copyOf(parsedEntries)
                    )
            );

            log.info(
                    "Loaded " + parsedEntries.size()
                            + " entries from category " + categoryName
            );
        }

        return new BingoItemManager(result);
    }

    private static ParsedEntry parseEntry(
            Entry entry,
            String category,
            Logger log
    ) {

        if (entry.itemNames == null || entry.itemNames.isEmpty()) {
            log.warning(
                    "[" + category + "] Entry has no item_names."
            );
            return null;
        }

        List<Material> materials = new ArrayList<>();

        for (String name : entry.itemNames) {

            Material material = parseMaterial(name);

            if (material == null) {
//                log.warning(
//                        "[" + category + "] Unknown material: " + name
//                );
                throw new RuntimeException(String.format("[%s] Unknown material: %s", category, name));
            }

            materials.add(material);
        }

        if (materials.isEmpty()) {
            log.warning(
                    "[" + category + "] Entry has no valid materials."
            );
            return null;
        }

        Set<Biome> biomes = new LinkedHashSet<>();

        if (entry.foundInBiomes != null) {
            for (String id : entry.foundInBiomes) {

                Biome biome = parseBiome(id);

                if (biome == null) {
                    throw new RuntimeException(String.format("[%s] Unknown biome: %s", category, id));
                } else {
                    biomes.add(biome);
                }
            }
        }

        Set<Structure> structures = new LinkedHashSet<>();

        if (entry.foundInStructures != null) {
            for (String id : entry.foundInStructures) {

                Structure structure = parseStructure(id);

                if (structure == null) {
                    throw new RuntimeException(String.format("[%s] Unknown structure: %s", category, id));

                } else {
                    structures.add(structure);
                }
            }
        }

        int choosing = Math.max(
                1,
                Math.min(entry.choosing, materials.size())
        );

        return new ParsedEntry(
                List.copyOf(materials),
                choosing,
                Collections.unmodifiableSet(biomes),
                Collections.unmodifiableSet(structures)
        );
    }

    // ------------------------------------------------------------
    // MATERIAL / BIOME / STRUCTURE PARSING
    // ------------------------------------------------------------

    public static Material parseMaterial(String name) {
        return Material.matchMaterial(name);
    }

    public static Biome parseBiome(String id) {

        NamespacedKey key = NamespacedKey.fromString(
                id.toLowerCase(Locale.ROOT)
        );

        if (key == null) {
            return null;
        }

        Registry<Biome> registry =
                RegistryAccess.registryAccess()
                        .getRegistry(RegistryKey.BIOME);

        return registry.get(key);
    }

    public static Structure parseStructure(String id) {

        NamespacedKey key = NamespacedKey.fromString(
                id.toLowerCase(Locale.ROOT)
        );

        if (key == null) {
            return null;
        }

        Registry<Structure> registry =
                RegistryAccess.registryAccess()
                        .getRegistry(RegistryKey.STRUCTURE);

        return registry.get(key);
    }

    // ------------------------------------------------------------
    // ITEM SELECTION
    // ------------------------------------------------------------

    /**
     * Gets a specified number of unique random materials
     * from a category.
     *
     * Each JSON entry is considered once. Its "choosing"
     * property determines how many materials can be selected
     * from that entry.
     *
     * @param category Category name, e.g. "Common"
     * @param amount Number of materials to retrieve
     * @return List of randomly selected materials
     */
    public List<Material> getMaterials(String category, int amount) {

        if (amount <= 0) {
            return Collections.emptyList();
        }

        ParsedCategory parsedCategory = getCategory(category);

        if (parsedCategory == null) {
            throw new IllegalArgumentException(
                    "Unknown bingo category: " + category
            );
        }

        List<ParsedEntry> entries = new ArrayList<>(
                parsedCategory.entries()
        );

        Collections.shuffle(entries);

        Set<Material> selected = new LinkedHashSet<>();

        for (ParsedEntry entry : entries) {

            List<Material> candidates = new ArrayList<>(
                    entry.materials()
            );

            Collections.shuffle(candidates);

            int choosing = Math.min(
                    entry.choosing(),
                    candidates.size()
            );

            for (int i = 0; i < choosing; i++) {

                Material material = candidates.get(i);

                selected.add(material);

                if (selected.size() >= amount) {
                    return new ArrayList<>(selected);
                }
            }
        }

        if (selected.size() < amount) {
            throw new IllegalArgumentException(
                    "Requested " + amount + " items from category "
                            + category + ", but only "
                            + selected.size() + " unique materials exist."
            );
        }

        return new ArrayList<>(selected);
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

    public static boolean isInBiome(
            Location location,
            Set<Biome> biomes
    ) {

        Biome current = location.getWorld().getBiome(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        return biomes.contains(current);
    }

    public static boolean isInStructure(
            Location location,
            Set<Structure> structures
    ) {

        Chunk chunk = location.getChunk();

        for (Structure structure : structures) {

            for (GeneratedStructure generated :
                    chunk.getStructures(structure)) {

                if (generated.getBoundingBox().contains(
                        location.toVector()
                )) {
                    return true;
                }
            }
        }

        return false;
    }
}
