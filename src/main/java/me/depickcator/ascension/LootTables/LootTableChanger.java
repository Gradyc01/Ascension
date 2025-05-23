package me.depickcator.ascension.LootTables;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public interface LootTableChanger {
    String NAMESPACED_KEY1 = "ascension";
    String NAMESPACED_KEY2 = "loot_table";
    NamespacedKey namespacedKey = new NamespacedKey(NAMESPACED_KEY1, NAMESPACED_KEY2);
    HashMap<String, LootTableChanger> items = new HashMap<>();
    ItemStack getItem();
    /*Upon triggering a certain LootTable event.
    * Returns true if successful false otherwise*/
    boolean uponEvent(Event e, Player p);
    /*Registers an Item/Block into the LootTable*/
    void registerItem();

    default boolean isEventBreakBlockEvent(Event e) {
        return e instanceof BlockBreakEvent;
    }

    /*Adds a Loot Table Item into Items*/
    default void addItem(ItemStack item, LootTableChanger lootTableChanger) {
        String itemKey = item.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
        items.put(itemKey, lootTableChanger);
    }

    /*Adds a Loot Table Entity into Items*/
    default void addEntity(String entityString, LootTableChanger lootTableChanger) {
        items.put(entityString, lootTableChanger);
    }

    /*Adds a Loot Table Block into Items*/
    default void addBlock(Material material, LootTableChanger lootTableChanger) {
        if (material.isBlock()) {
            items.put(material.getBlockTranslationKey(), lootTableChanger);
        }
    }

    /*Adds a Persistent Data Tag onto an ItemStack*/
    static void addPersistentDataForItems(ItemStack item, String KEY) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(namespacedKey, PersistentDataType.STRING, KEY);
        item.setItemMeta(meta);
    }

    static LootTableChanger findItem(ItemStack item) {
        try {
            if (item.getItemMeta().getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING)) {
                String key = item.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
                return items.get(key);
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    static LootTableChanger findEntity(Entity entity) {
        return items.get(entity.getType().translationKey());
    }

    static LootTableChanger findBlock(Block block) {
        return items.get(block.translationKey());
    }
}
