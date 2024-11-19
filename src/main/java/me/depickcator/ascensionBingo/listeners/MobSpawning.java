package me.depickcator.ascensionBingo.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.depickcator.ascensionBingo.AscensionBingo;
import net.kyori.adventure.bossbar.BossBar.Listener;

public class MobSpawning implements Listener {
    private final AscensionBingo plugin;
    private final ArrayList<EntityType> superMobs = new ArrayList<>();
    public MobSpawning(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        event.getEntity().getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(15);
        Random r = new Random();
        if (superMobs.contains(event.getEntityType()) && r.nextDouble() <= 0.025) {
            switch (event.getEntityType()) {
                case ZOMBIE -> {
                    zombieSuper((Zombie) event.getEntity());
                }
                case SPIDER -> {
                    spiderSuper((Spider) event.getEntity());
                }
                case SKELETON -> {
                    skeletonSuper((Skeleton) event.getEntity());
                }
                case CREEPER -> {
                    creeperSuper((Creeper) event.getEntity());
                }
                default -> {
                    //Ignored
                }
            }
        }
    }

    private void zombieSuper(Zombie e) {
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
    }
    private void skeletonSuper(Skeleton e) {
        e.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        e.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        EntityEquipment equipment = e.getEquipment();
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
    }
    private void creeperSuper(Creeper e) {
        e.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        e.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        e.setPowered(true);
        e.setExplosionRadius(20);
    }
    private void spiderSuper(Spider e) {
        e.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.2);
        e.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.45);
    }

    private void setZeroDropChance(EntityEquipment equipment) {
        equipment.setItemInMainHandDropChance(0.0f);
        equipment.setItemInOffHandDropChance(0.0f);
        equipment.setHelmetDropChance(0.0f);
        equipment.setChestplateDropChance(0.0f);
        equipment.setLeggingsDropChance(0.0f);
        equipment.setBootsDropChance(0.0f);
    }
}



