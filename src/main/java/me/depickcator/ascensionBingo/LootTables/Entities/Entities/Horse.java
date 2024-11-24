package me.depickcator.ascensionBingo.LootTables.Entities.Entities;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascensionBingo.LootTables.Entities.EntityLootTable;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Horse implements LootTableChanger, EntityLootTable {
    private final AscensionBingo plugin;
    public static String KEY = EntityType.HORSE.translationKey();
    public Horse(AscensionBingo plugin) {
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
            e.getDrops().clear();
            giveForagingExp(p, ForageBlocks.FORAGING_UNCOMMON);

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());
            int leatherCount = calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1);
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
