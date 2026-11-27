package org.bingo4.Player.Data;

import org.bingo4.Bingo4;
import org.bingo4.Player.Interfaces.WeaponInfo;
import org.bingo4.Utility.TextUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static org.bingo4.Player.Interfaces.WeaponInfo.MELEE;
import static org.bingo4.Player.Interfaces.WeaponInfo.PROJECTILE;
import static org.bingo4.Teams.TeamComeback.COMEBACK_TIER;

public class PlayerItems implements PlayerDataObservers{

    private static final NamespacedKey WEAPON_WIELDER_KEY = new NamespacedKey(Bingo4.getInstance(), "weapon_wielder");

    private final PlayerData playerData;

    private WeaponInfo meleeWeapon;
    private WeaponInfo projectileWeapon;

    public PlayerItems(PlayerData playerData) {
        this.playerData = playerData;
    }

    public ItemStack registerPlayerWeapon(ItemStack item, String weaponType) {
        Damageable meta = (Damageable) item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String weaponId = playerData.getPlayer().getName() + "-" + UUID.randomUUID();
        container.set(WEAPON_WIELDER_KEY, PersistentDataType.STRING, weaponId);
        container.set(COMEBACK_TIER, PersistentDataType.INTEGER, 0);
        item.setItemMeta(meta);
        switch (weaponType) {
            case MELEE -> meleeWeapon = new WeaponInfo(weaponId, List.of(Enchantment.SHARPNESS), MELEE);
            case PROJECTILE -> projectileWeapon = new WeaponInfo(weaponId, List.of(Enchantment.POWER), PROJECTILE);
            default -> throw new RuntimeException(String.format("Weapon type must be either %s or %s", MELEE, PROJECTILE));
        }
        return item;
    }

    public ItemStack changePlayerWeaponTier(int tierChange, String weaponType, NamespacedKey tierType) {
        WeaponInfo weaponInfo = getWeaponInfo(weaponType);
//
        ItemStack item = getWeaponItemStack(playerData.getPlayer().getInventory(), weaponInfo);
        if (item == null) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        PersistentDataContainer container = meta.getPersistentDataContainer();

        int tierLevel = container.has(tierType, PersistentDataType.INTEGER)
                ? container.get(tierType, PersistentDataType.INTEGER)
                : 0;

        int newTierLevel = Math.max(0, tierLevel + tierChange);

        return setPlayerWeaponTier(newTierLevel, weaponType, tierType);
    }

    public ItemStack setPlayerWeaponTier(int newTierLevel, String weaponType, NamespacedKey tierType) {
        WeaponInfo weaponInfo = getWeaponInfo(weaponType);

        ItemStack item = getWeaponItemStack(playerData.getPlayer().getInventory(), weaponInfo);
        if (item == null) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        PersistentDataContainer container = meta.getPersistentDataContainer();

        int tierLevel = container.has(tierType, PersistentDataType.INTEGER)
                ? container.get(tierType, PersistentDataType.INTEGER)
                : 0;

        int tierChange = newTierLevel - tierLevel;

        for (Enchantment enchantment : weaponInfo.enchantments()) {
            int newEnchantmentLevel = meta.getEnchantLevel(enchantment) + tierChange;

            if (newEnchantmentLevel > 0) {
                meta.addEnchant(enchantment, newEnchantmentLevel, true);
            } else {
                meta.removeEnchant(enchantment);
            }
        }

        container.set(tierType, PersistentDataType.INTEGER, newTierLevel);
        item.setItemMeta(meta);

        return item;
    }

    private WeaponInfo getWeaponInfo(String weaponType) {
        return switch (weaponType) {
            case MELEE -> meleeWeapon;
            case PROJECTILE -> projectileWeapon;
            default -> throw new RuntimeException(String.format("Weapon type must be either %s or %s", MELEE, PROJECTILE));
        };
    }

    private ItemStack getWeaponItemStack(PlayerInventory playerInventory, WeaponInfo weaponInfo) {
       for (ItemStack item : playerInventory.getContents()) {
           if (item == null || item.getType().isAir()) continue;
           ItemMeta meta = item.getItemMeta();
           PersistentDataContainer container = meta.getPersistentDataContainer();
           if (container.has(WEAPON_WIELDER_KEY, PersistentDataType.STRING)) {
               if (container.get(WEAPON_WIELDER_KEY, PersistentDataType.STRING).equals(weaponInfo.weaponUUID())) {
                   return item;
               }
           }
       }
       TextUtil.debugText(String.format("[%s items] weapon type '%s' name '%s' has been lost",
               playerData.getPlayer().getName(),
               weaponInfo.weaponType(),
               weaponInfo.weaponUUID()), Level.SEVERE);
       return null;
    }

    @Override
    public void updatePlayer() {

    }
}
