package me.depickcator.ascensionBingo.LootTables.Entities.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.Entities.EntityLootTable;
import me.depickcator.ascensionBingo.LootTables.Entities.EntityUtil;
import me.depickcator.ascensionBingo.LootTables.Entities.Superable;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SpiderEntity implements LootTableChanger, EntityLootTable, Superable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.SPIDER.translationKey();
    public SpiderEntity(AscensionBingo plugin) {
        this.plugin = plugin;
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        try {
            EntityDeathEvent e = getEntityDeathEvent(event);
            e.getDrops().clear();
            if (isSuperEntity(e.getEntity())) {
                giveCombatExp(p, EntityUtil.COMBAT_VERY_RARE);
                lootFromSuperEntity(e.getEntity());
                return true;
            }

            giveCombatExp(p, EntityUtil.COMBAT_COMMON);
            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            if (e.getEntityType() == EntityType.SPIDER) {
                int stringCount = calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1);
                if (stringCount > 0) {
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.STRING, stringCount));
                }

                // Pool 2: Drop spider eye with looting effects
                int spiderEyeCount = calculateUniformRandom(r, -1, 1) + calculateLootingBonus(r, lootingLevel, 0, 1);
                if (spiderEyeCount > 0) {
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.SPIDER_EYE, spiderEyeCount));
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }




    @Override
    public void registerItem() {
        addEntity(KEY,this);
    }



    @Override
    public void superEntity(Entity e) {
        Spider spider = (Spider) e;
        spider.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
        tagSuperEntity(spider);
    }

    @Override
    public void lootFromSuperEntity(Entity e) {
        e.getWorld().dropItem(e.getLocation(), new ItemStack(Material.FERMENTED_SPIDER_EYE));
    }
}