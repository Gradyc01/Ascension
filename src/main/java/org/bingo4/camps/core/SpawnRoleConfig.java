package org.bingo4.camps.core;

import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SpawnRoleConfig {
    private final YamlConfiguration yaml;

    public SpawnRoleConfig(File file) {
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    public Map<LocalPos, SpawnRole> rolesFor(String templateId) {
        Map<LocalPos, SpawnRole> result = new HashMap<>();
        ConfigurationSection section = yaml.getConfigurationSection(
                "camps." + templateId + ".spawn-roles");
        if (section == null) return result;

        for (String key : section.getKeys(false)) {
            String[] parts = key.split(",");
            if (parts.length != 3) continue;
            try {
                LocalPos position = new LocalPos(
                        Integer.parseInt(parts[0].trim()),
                        Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim()));
                SpawnRole role = SpawnRole.valueOf(section.getString(key, "UNASSIGNED")
                        .trim().toUpperCase());
                result.put(position, role);
            } catch (RuntimeException ignored) {
                // Invalid marker config remains UNASSIGNED.
            }
        }
        return result;
    }
}
