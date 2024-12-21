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
import org.bukkit.material.Colorable;

import java.util.Random;

public class Sheep implements LootTableChanger, EntityLootTable {
    public static String KEY = EntityType.SHEEP.translationKey();
    public Sheep() {
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
            Colorable entity = (Colorable) e.getEntity();

            giveForagingExp(p, ForageBlocks.FORAGING_UNCOMMON);
            e.getDrops().clear();

            Random r = new Random();
            int lootingLevel = getLootingLevel(e.getEntity().getKiller());

            Material woolMaterial = getWoolMaterial(entity);
            if (woolMaterial != null) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(woolMaterial, 1));
            }

            int rawMuttonCount = calculateUniformRandom(r, 1, 2) + calculateLootingBonus(r, lootingLevel, 0, 1);
            if (PlayerUtil.getPlayerData(p).getPlayerStats().isFoodDrops()) {
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.MUTTON, rawMuttonCount));
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



    private Material getWoolMaterial(Colorable sheep) {
        return switch (sheep.getColor()) {
            case WHITE -> Material.WHITE_WOOL;
            case BLACK -> Material.BLACK_WOOL;
            case GRAY -> Material.GRAY_WOOL;
            case LIGHT_GRAY -> Material.LIGHT_GRAY_WOOL;
            case BROWN -> Material.BROWN_WOOL;
            case PINK -> Material.PINK_WOOL;
            case RED -> Material.RED_WOOL;
            case ORANGE -> Material.ORANGE_WOOL;
            case YELLOW -> Material.YELLOW_WOOL;
            case LIME -> Material.LIME_WOOL;
            case GREEN -> Material.GREEN_WOOL;
            case CYAN -> Material.CYAN_WOOL;
            case LIGHT_BLUE -> Material.LIGHT_BLUE_WOOL;
            case BLUE -> Material.BLUE_WOOL;
            case PURPLE -> Material.PURPLE_WOOL;
            case MAGENTA -> Material.MAGENTA_WOOL;
            case null, default -> null;
        };
    }


}
