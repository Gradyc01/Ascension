package org.bingo4.camps.storage;

import org.bingo4.Bingo4;
import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/** Persists processed region keys and camp marker coordinates to camps.yml. */
public final class CampStore {
    private final File file;
    private final YamlConfiguration yaml;

    public CampStore(File file) {
        this.file = file;
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    public Set<String> loadProcessedRegions() {
        return new HashSet<>(yaml.getStringList("processed-regions"));
    }

    public List<CampInstance> loadCamps() {
        List<CampInstance> result = new ArrayList<>();
        for (Map<?, ?> map : yaml.getMapList("camps")) {
            try {
                String instanceId = string(map, "instance-id");
                String templateId = string(map, "template-id");
                CampRotation rotation = CampRotation.valueOf(string(map, "rotation"));
                Location origin = readPosition((Map<?, ?>) map.get("origin"));

                List<SpawnPoint> spawns = new ArrayList<>();
                Object spawnValue = map.get("spawn-points");
                if (spawnValue instanceof List<?> list) {
                    for (Object entry : list) {
                        if (!(entry instanceof Map<?, ?> entryMap)) continue;
                        spawns.add(new SpawnPoint(
                                readPosition((Map<?, ?>) entryMap.get("position")),
                                SpawnRole.valueOf(string(entryMap, "role"))));
                    }
                }

                List<CommandPanel> panels = new ArrayList<>();
                Object panelValue = map.get("command-panels");
                if (panelValue instanceof List<?> list) {
                    for (Object entry : list) {
                        if (!(entry instanceof Map<?, ?> entryMap)) continue;
                        panels.add(new CommandPanel(
                                readPosition((Map<?, ?>) entryMap.get("position")),
                                Material.valueOf(string(entryMap, "material")),
                                BlockFace.valueOf(string(entryMap, "facing")),
                                string(entryMap, "command")));
                    }
                }
                result.add(new CampInstance(instanceId, templateId, rotation,
                        origin, spawns, panels));
            } catch (RuntimeException ignored) {
                // Skip malformed entries rather than preventing the plugin from starting.
            }
        }
        return result;
    }

    public void save(Set<String> processedRegions, Collection<CampInstance> camps) throws IOException {
        yaml.set("processed-regions", processedRegions.stream().sorted().toList());
        List<Map<String, Object>> serialized = new ArrayList<>();
        for (CampInstance camp : camps) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("instance-id", camp.instanceId());
            row.put("template-id", camp.templateId());
            row.put("rotation", camp.rotation().name());
            row.put("origin", positionMap(camp.origin()));

            List<Map<String, Object>> spawns = new ArrayList<>();
            for (SpawnPoint spawn : camp.spawnPoints()) {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("position", positionMap(spawn.markerPosition()));
                entry.put("role", spawn.role().name());
                spawns.add(entry);
            }
            row.put("spawn-points", spawns);

            List<Map<String, Object>> panels = new ArrayList<>();
            for (CommandPanel panel : camp.commandPanels()) {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("position", positionMap(panel.position()));
                entry.put("material", panel.material().name());
                entry.put("facing", panel.facing().name());
                entry.put("command", panel.command());
                panels.add(entry);
            }
            row.put("command-panels", panels);
            serialized.add(row);
        }
        yaml.set("camps", serialized);
        yaml.save(file);
    }

    private Map<String, Object> positionMap(Location pos) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("world", pos.getWorld().getUID().toString());
        result.put("x", pos.x());
        result.put("y", pos.y());
        result.put("z", pos.z());
        return result;
    }

    private Location readPosition(Map<?, ?> map) {
        return new Location(Bingo4.getInstance().getServer().getWorld(UUID.fromString(string(map, "world"))),
                number(map, "x"), number(map, "y"), number(map, "z"));
    }

    private String string(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (value == null) throw new IllegalArgumentException("Missing " + key);
        return String.valueOf(value);
    }

    private int number(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (!(value instanceof Number number)) throw new IllegalArgumentException("Missing number " + key);
        return number.intValue();
    }
}
