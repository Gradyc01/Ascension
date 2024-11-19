package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SkeletonEntity implements LootTableChanger, EntityLootTable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.SKELETON.translationKey();
    public SkeletonEntity(AscensionBingo plugin) {
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
            giveCombatExp(p, EntityUtil.COMBAT_COMMON);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            if (e.getEntityType() == EntityType.SKELETON) {
                lootPoolConstant(e, lootingLevel, r);
                lootPoolBonus(e, lootingLevel, r);
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }



    private void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r) {
        int min = 1, max = 2;
        int count = (int) (min + (max - min) * r.nextDouble());

        // Apply looting bonus
        int c1 = count +  (int) (r.nextDouble() * lootingLevel);
        int c2 = count +  (int) (r.nextDouble() * lootingLevel);

        if (c1 > 0) {
            ItemStack item = new ItemStack(Material.BONE, count);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
        }

        if (c2 > 0) {
            ItemStack item = new ItemStack(Material.ARROW, count);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
        }
    }

    private void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r) {
        if (r.nextDouble() <= calculateLootChance(lootingLevel, 0.020, 0.030, 0.01)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.SKELETON_SKULL));
        }
    }


    @Override
    public void registerItem() {
        addEntity(KEY,this);
    }

    private double calculateLootChance(int lootingLevel, double baseChance, double enchantedBase, double perLevel) {
        if (lootingLevel > 0) {
            return enchantedBase + perLevel * (lootingLevel - 1);
        }
        return baseChance;
    }
}
