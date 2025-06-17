package me.depickcator.ascension.MainMenuUI.Settings.Settings;

import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Material;

public abstract class SettingButton {
    private final String name;
    private final String description;
    private final String key;
    private final Material material;
    public SettingButton(String name, String description, String key, Material material) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.material = material;
    }

    public abstract boolean onClick(PlayerData pD, boolean newValue);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public Material getMaterial() {
        return material;
    }
}
