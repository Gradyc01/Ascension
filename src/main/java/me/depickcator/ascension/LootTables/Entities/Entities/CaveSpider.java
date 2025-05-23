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

public class CaveSpider implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.CAVE_SPIDER.translationKey();
    public CaveSpider() {
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
            giveCombatExp(p, SkillExpAmount.COMBAT_UNCOMMON.getExp());
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int stringCount = (int) (calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1));
            if (stringCount > 0) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.STRING, stringCount));
            }
            int spiderEyeCount = (int) (calculateUniformRandom(r, 1, 1) + calculateLootingBonus(r, lootingLevel, 0, 1));
            if (spiderEyeCount > 0) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.SPIDER_EYE, spiderEyeCount));
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
