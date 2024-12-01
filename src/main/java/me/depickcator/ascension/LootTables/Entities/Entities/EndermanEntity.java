package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.Entities.EntityUtil;
import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EndermanEntity implements LootTableChanger, EntityLootTable {
    private final Ascension plugin;
    public static String KEY = EntityType.ENDERMAN.translationKey();
    public EndermanEntity(Ascension plugin) {
        this.plugin = plugin;
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
            giveCombatExp(p, EntityUtil.COMBAT_UNCOMMON);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            if (e.getEntityType() == EntityType.ENDERMAN) {
                lootPoolConstant(e, lootingLevel, r);
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    private void lootPoolConstant(EntityDeathEvent e, int lootingLevel, Random r) {
        int min = 1, max = 2;
        int count = (int) (min + (max - min) * r.nextDouble());

        // Apply looting bonus
        count += (int) (r.nextDouble() * lootingLevel);

        if (count > 0) {
            ItemStack item = new ItemStack(Material.ENDER_PEARL, count);
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), item);
        }
    }


    @Override
    public void registerItem() {
        addEntity(KEY,this);
    }


}
