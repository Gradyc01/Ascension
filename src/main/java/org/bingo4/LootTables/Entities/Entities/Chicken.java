package org.bingo4.LootTables.Entities.Entities;

import org.bingo4.LootTables.Entities.EntityLootTable;
import org.bingo4.LootTables.LootTableChanger;
import org.bingo4.Player.Data.PlayerStats;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static org.bukkit.entity.Chicken.Variant.COLD;
import static org.bukkit.entity.Chicken.Variant.WARM;

public class Chicken implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.CHICKEN.translationKey();
    public Chicken() {
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

            org.bukkit.entity.Chicken chicken = (org.bukkit.entity.Chicken) e.getEntity();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int featherCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0.5, 1));
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.FEATHER, featherCount));

            int rawChickenCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0.5, 1));
            if (PlayerUtil.getPlayerData(p).getPlayerStats().getSetting(PlayerStats.foodDropsKey)) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.CHICKEN, rawChickenCount));
            }
            int rawEggCount = !chicken.isAdult() ? 1 : (int) (calculateUniformRandom(r, -2, 1) + calculateLootingBonus(r, lootingLevel, 0.5, 1));
            if (rawEggCount > 0) {
                Material eggItem;

                if (chicken.getVariant() == org.bukkit.entity.Chicken.Variant.WARM) {
                    eggItem = Material.BROWN_EGG;
                } else if (chicken.getVariant() == org.bukkit.entity.Chicken.Variant.COLD) {
                    eggItem = Material.BLUE_EGG;
                } else {
                    eggItem = Material.EGG;
                }
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(eggItem , rawEggCount));
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
