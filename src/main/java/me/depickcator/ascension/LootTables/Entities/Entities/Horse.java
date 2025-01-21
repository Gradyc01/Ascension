package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Horse implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.HORSE.translationKey();
    public Horse() {
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
            giveForagingExp(p, SkillExpAmount.FORAGING_UNCOMMON.getExp());

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int leatherCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1));
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.LEATHER, leatherCount));

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
