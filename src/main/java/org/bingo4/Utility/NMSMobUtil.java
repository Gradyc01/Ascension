package org.bingo4.Utility;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.bingo4.Mobs.ItemDisplayDetection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class NMSMobUtil {
    public static void generateRandomArmor(EntityEquipment equipment, Random r) {
        setArmorPiece(equipment, EquipmentSlot.HEAD, r);
        setArmorPiece(equipment, EquipmentSlot.CHEST, r);
        setArmorPiece(equipment, EquipmentSlot.LEGS, r);
        setArmorPiece(equipment, EquipmentSlot.FEET, r);
    }

    private static void setArmorPiece(EntityEquipment equipment, EquipmentSlot slot, Random r) {
        Item item = Mob.getEquipmentForSlot(slot, generateNumber(r));
        if (item != null) {
            equipment.set(slot, new net.minecraft.world.item.ItemStack(item));
        }
    }

    private static int generateNumber(Random random) {
        if (random.nextDouble() < 0.75) {
            return -1;
        } else if (random.nextDouble() < 0.83) {
            return 0;
        } else if (random.nextDouble() < 0.90) {
            return 1;
        } else if (random.nextDouble() < 0.95) {
            return 2;
        } else if (random.nextDouble() < 0.98) {
            return 3;
        }
        return 4;
    }

    public static Component generateHealthText(Component name, LivingEntity entity) {
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        String healthText = "    " + String.format("%.1f", health) + "/" + Math.round(maxHealth) + "❤";
        return name.copy().append(Component.literal(healthText).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TextUtil.AQUA.value()))));
    }

    public static void attemptToDropItemStack(ItemStack item, DamageSource damageSource, Entity entity, double dropChance) {
        AttributeInstance attribute = null;
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) attribute = livingEntity.getAttribute(Attributes.LUCK);
        double chance = dropChance * (attribute == null ? 1.0 : attribute.getValue() + 1.0);
        RandomSource random = entity.getRandom();
        float num = random.nextFloat();
        TextUtil.debugText(num + " v.s." + chance);
        Location location = entity.getBukkitEntity().getLocation().clone().add(random.nextFloat() - 0.5, 1, random.nextFloat() - 0.5);
        if (num < chance) {
            if (dropChance <= 0.20) {
                dropRareItem(item, location, damageSource, dropChance);
            } else if (dropChance <= 0.50) {
                dropCommonItem(item, location, damageSource);
            } else {
                new ItemDisplayDetection(location, item);
            }
        }
    }

    private static void dropRareItem(ItemStack item, Location location, DamageSource damageSource, double dropChance) {
        if (damageSource.getEntity() instanceof Player player) {
            org.bukkit.entity.Player p = (org.bukkit.entity.Player) player.getBukkitEntity();
            p.sendMessage(TextUtil.makeText("RARE DROP!   ", TextUtil.GOLD, true, true)
                    .append(TextUtil.makeText(TextUtil.getItemNameString(item), TextUtil.YELLOW))
                    .append(TextUtil.makeText("      (" +String.format("%.2f", dropChance * 100) + "%)", TextUtil.AQUA)));
        }
        new ItemDisplayDetection(location, item);
    }

    private static void dropCommonItem(ItemStack item, Location location, DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player) {
            org.bukkit.entity.Player p = (org.bukkit.entity.Player) player.getBukkitEntity();
            p.sendMessage(TextUtil.makeText("UNCOMMON DROP!   ", TextUtil.GREEN, true, false)
                    .append(TextUtil.makeText(TextUtil.getItemNameString(item), TextUtil.YELLOW)));
        }
        new ItemDisplayDetection(location, item);
    }

    public static void setAndSpawn(Entity entity, Location location) {
        entity.setPosRaw(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(entity);
    }

    public static void removeAllEquipmentDropChance(Mob entity) {
        entity.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        entity.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        entity.setDropChance(EquipmentSlot.HEAD, 0.0F);
        entity.setDropChance(EquipmentSlot.CHEST, 0.0F);
        entity.setDropChance(EquipmentSlot.LEGS, 0.0F);
        entity.setDropChance(EquipmentSlot.FEET, 0.0F);
    }
}