package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CreeperEntity implements LootTableChanger, EntityLootTable, Superable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.CREEPER.translationKey();
    public CreeperEntity(AscensionBingo plugin) {
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

            if (isSuperEntity(e.getEntity())) {
                e.getDrops().clear();
                lootFromSuperEntity(e.getEntity());
                giveCombatExp(p, EntityUtil.COMBAT_VERY_RARE);
                return true;
            }

            giveCombatExp(p, EntityUtil.COMBAT_COMMON);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            lootPoolConstant(e, lootingLevel, r);
            lootPoolBonus(e, lootingLevel, r);

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    private void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r) {

        double stringCount = calculateUniformRandom(r, 1, 2);
        if (lootingLevel > 0) {
            stringCount+=calculateUniformRandom(r, 0, 1);
        }

        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.GUNPOWDER, (int) stringCount));

    }
    private void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r) {
        double chance = calculateLootChance(lootingLevel, 0.02, 0.03, 0.01);
        if (r.nextDouble() <= chance) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.CREEPER_HEAD));
        }
    }

    private double calculateUniformRandom(Random r, int min, int max) {
        return (min + (max - min) * r.nextDouble());
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

    @Override
    public void superEntity(Entity e) {
        Creeper creeper = (Creeper) e;
        creeper.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        creeper.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        creeper.setPowered(true);
        creeper.setExplosionRadius(20);
        tagSuperEntity(creeper);
    }

    @Override
    public void lootFromSuperEntity(Entity e) {
        e.getWorld().dropItem(e.getLocation(), new ItemStack(Material.CREEPER_HEAD));
    }
}
