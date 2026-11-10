package org.bingo4.LootTables.Entities.Entities;

import org.bingo4.LootTables.Entities.EntityLootTable;
import org.bingo4.LootTables.LootTableChanger;
import org.bingo4.Player.Data.PlayerStats;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Pig implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.PIG.translationKey();
    public Pig() {
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
            if (!PlayerUtil.getPlayerData(p).getPlayerStats().getSetting(PlayerStats.foodDropsKey)) {
                e.getDrops().clear();
            }

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }




    @Override
    public void registerItem() {
        addEntity(KEY,this);
    }


}
