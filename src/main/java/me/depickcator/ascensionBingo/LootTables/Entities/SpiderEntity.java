package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.LootTableChanger;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SpiderEntity implements LootTableChanger, EntityLootTable {
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
            giveCombatExp(p);
            e.getDrops().clear();

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
        plugin.getServer().broadcast(TextUtil.makeText(KEY, TextUtil.WHITE));
        addEntity(KEY,this);
    }

    private int getLootingLevel(Player killer) {
        if (killer != null) {
            // killer.getInventory().getItemInMainHand().getEnchantments() != null
            return killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
        }
        return 0;
    }

    private int calculateUniformRandom(Random r, int min, int max) {
        return (int) (min + (max - min) * r.nextDouble());
    }

    private int calculateLootingBonus(Random r, int lootingLevel, double min, double max) {
        if (lootingLevel > 0) {
            return (int) (min + (max - min) * r.nextDouble() * lootingLevel);
        }
        return 0;
    }
}
