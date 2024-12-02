package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.LootTables.Entities.Superable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class SkeletonEntity implements LootTableChanger, EntityLootTable, Superable {
    public static String KEY = EntityType.SKELETON.translationKey();
    public SkeletonEntity() {
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

            Random r = new Random();
            giveCombatExp(p, EntityUtil.COMBAT_COMMON);
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            lootPoolConstant(e, lootingLevel, r);
            lootPoolBonus(e, lootingLevel, r);

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

    @Override
    public void superEntity(Entity e) {
        Skeleton skeleton = (Skeleton) e;
        skeleton.getAttribute(Attribute.ARMOR).setBaseValue(30);
        skeleton.getAttribute(Attribute.SCALE).setBaseValue(1.2);
        EntityEquipment equipment = skeleton.getEquipment();
        equipment.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        equipment.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.POWER, 16, true);
        bowMeta.addEnchant(Enchantment.FLAME, 1, true);
        bow.setItemMeta(bowMeta);
        equipment.setItemInMainHand(bow);
        setZeroDropChance(equipment);
        tagSuperEntity(skeleton);
    }

    @Override
    public void lootFromSuperEntity(Entity e) {
        e.getWorld().dropItem(e.getLocation(), new ItemStack(Material.SKELETON_SKULL));
    }
}
