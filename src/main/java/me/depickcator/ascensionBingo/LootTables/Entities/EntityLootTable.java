package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;


public interface EntityLootTable {
    default void giveCombatExp(Player p, int amount) throws Exception {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) throw new Exception("No PlayerData");
        pD.getPlayerSkills().getCombat().addExp(amount);
    }

    default void giveForagingExp(Player p, int amount) throws Exception {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) throw new Exception("No PlayerData");
        pD.getPlayerSkills().getForaging().addExp(amount);
    }

    default EntityDeathEvent getEntityDeathEvent(Event event) throws Exception {
        if (!(event instanceof EntityDeathEvent)) throw new Exception("Incorrect type");
        return (EntityDeathEvent) event;
    }

    default int getLootingLevel(Player killer) {
        if (killer != null) {
            return killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
        }
        return 0;
    }

    default int calculateUniformRandom(Random r, int min, int max) {
        return (int) (min + (max - min) * r.nextDouble());
    }

    default int calculateLootingBonus(Random r, int lootingLevel, double min, double max) {
        if (lootingLevel > 0) {
            return (int) (min + (max - min) * r.nextDouble() * lootingLevel);
        }
        return 0;
    }
//
//    void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r);
//    void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r);
}
