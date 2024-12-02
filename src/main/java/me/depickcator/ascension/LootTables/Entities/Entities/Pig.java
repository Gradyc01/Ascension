package me.depickcator.ascension.LootTables.Entities.Entities;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.PlayerUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Pig implements LootTableChanger, EntityLootTable {
    private final Ascension plugin;
    public static String KEY = EntityType.PIG.translationKey();
    public Pig(Ascension plugin) {
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
            giveForagingExp(p, ForageBlocks.FORAGING_UNCOMMON);
            if (!PlayerUtil.getPlayerData(p).getPlayerStats().isFoodDrops()) {
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
