package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
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
            giveForagingExp(p, SkillExpAmount.FORAGING_UNCOMMON.getExp());
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int featherCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0.5, 1));
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.FEATHER, featherCount));

            int rawChickenCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0.5, 1));
            if (PlayerUtil.getPlayerData(p).getPlayerStats().getSetting(PlayerStats.foodDropsKey)) {
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
