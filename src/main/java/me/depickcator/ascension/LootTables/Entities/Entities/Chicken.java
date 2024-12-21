package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

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
            giveForagingExp(p, ForageBlocks.FORAGING_UNCOMMON);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int featherCount = calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.FEATHER, featherCount));

            int rawChickenCount = calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1);
            if (PlayerUtil.getPlayerData(p).getPlayerStats().isFoodDrops()) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.CHICKEN, rawChickenCount));
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
