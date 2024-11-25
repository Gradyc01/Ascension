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
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ZombieEntity implements LootTableChanger, EntityLootTable, Superable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.ZOMBIE.translationKey();
    public ZombieEntity(AscensionBingo plugin) {
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
                lootFromSuperEntity(e.getEntity());
                giveCombatExp(p, EntityUtil.COMBAT_VERY_RARE);
                return true;
            }

            giveCombatExp(p, EntityUtil.COMBAT_COMMON);
            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            if (e.getEntityType() == EntityType.ZOMBIE) {
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
        count += (int) (r.nextDouble() * lootingLevel);

        if (count > 0) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH, count);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
        }
    }

    private void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r) {
        if (r.nextDouble() < calculateLootChance(lootingLevel, 0.025, 0.035, 0.01)) {
            Material[] drops = {Material.IRON_INGOT, Material.CARROT, Material.POTATO};
            Material drop = drops[r.nextInt(drops.length)];
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(drop));
        }

        if (r.nextDouble() <= calculateLootChance(lootingLevel, 0.020, 0.030, 0.01)) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.ZOMBIE_HEAD));
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


    @Override
    public void superEntity(Entity entity) {
        Zombie e = (Zombie) entity;
        e.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        e.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
        e.setHealth(30);
        EntityEquipment equipment = e.getEquipment();
        equipment.setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        equipment.setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        equipment.setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
        equipment.setItemInOffHand(new ItemStack(Material.NETHERITE_AXE));
        setZeroDropChance(equipment);
        tagSuperEntity(e);
    }

    @Override
    public void lootFromSuperEntity(Entity e) {
        e.getWorld().dropItem(e.getLocation(), new ItemStack(Material.ZOMBIE_HEAD));
    }
}
