package org.bingo4.Player.Interfaces;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public record WeaponInfo(String weaponUUID, List<Enchantment> enchantments, String weaponType) {
    public static final String MELEE = "melee";
    public static final String PROJECTILE = "projectile";
}
