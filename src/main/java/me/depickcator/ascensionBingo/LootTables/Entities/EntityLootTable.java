package me.depickcator.ascensionBingo.LootTables.Entities;

import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public interface EntityLootTable {
    default void giveCombatExp(Player p) throws Exception {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) throw new Exception("No PlayerData");
        pD.getPlayerSkills().getCombat().addExp(EntityUtil.BASIC_COMBAT);
    }

    default EntityDeathEvent getEntityDeathEvent(Event event) throws Exception {
        if (!(event instanceof EntityDeathEvent)) throw new Exception("Incorrect type");
        return (EntityDeathEvent) event;
    }
//
//    void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r);
//    void lootPoolBonus(EntityDeathEvent e, int lootingLevel, Random r);
}
