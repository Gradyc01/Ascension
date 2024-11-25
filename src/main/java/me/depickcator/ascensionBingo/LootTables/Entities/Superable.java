package me.depickcator.ascensionBingo.LootTables.Entities;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataType;

public interface Superable {
    NamespacedKey namespacedKey = new NamespacedKey("ascension", "super_mob");
    void superEntity(Entity e);
    void lootFromSuperEntity(Entity e);
    default void setZeroDropChance(EntityEquipment equipment) {
        equipment.setItemInMainHandDropChance(0.0f);
        equipment.setItemInOffHandDropChance(0.0f);
        equipment.setHelmetDropChance(0.0f);
        equipment.setChestplateDropChance(0.0f);
        equipment.setLeggingsDropChance(0.0f);
        equipment.setBootsDropChance(0.0f);
    }
    default void tagSuperEntity(Entity e) {
        e.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, 1);
    }
    default boolean isSuperEntity(Entity e) {
        try {
            return e.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) == 1;
        } catch (Exception ignored) {
            return false;
        }

    }
}
