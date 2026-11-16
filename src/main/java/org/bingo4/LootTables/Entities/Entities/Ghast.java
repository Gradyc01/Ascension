package org.bingo4.LootTables.Entities.Entities;

import org.bingo4.LootTables.Entities.EntityLootTable;
import org.bingo4.LootTables.LootTableChanger;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Ghast implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.GHAST.translationKey();
    public Ghast() {
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

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int dropCount = (int) (calculateUniformRandom(r, 3, 5) + calculateLootingBonus(r, lootingLevel, 0, 3));
            if (dropCount > 0) {
                PlayerUtil.giveItem(p, new ItemStack(Material.GHAST_TEAR, dropCount));
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
