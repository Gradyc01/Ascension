package me.depickcator.ascensionBingo.Interfaces;

import me.depickcator.ascensionBingo.General.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public interface ShootsProjectiles {
    String METADATA = "projectiles";
    NamespacedKey key = new NamespacedKey("minecraft", METADATA);
    HashMap<String, ShootsProjectiles> projectiles = new HashMap<>();
    void applyKey(EntityShootBowEvent event);
    default void addProjectile(String key, ShootsProjectiles projectile) {
        projectiles.put(key, projectile);
    }
    void setProjectileComponent(EntityDamageByEntityEvent event);
    static ShootsProjectiles getProjectile(MetadataValue metadataValue) {
//        Bukkit.getServer().broadcast(TextUtil.makeText(metadataValue.asString()));
        return projectiles.get(metadataValue.asString());
    }
    static ShootsProjectiles getProjectile(ItemStack item) {
        try {

            return projectiles.get(item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING));
        } catch (Exception e) {
            return null;
        }

    }
}