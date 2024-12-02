package me.depickcator.ascension.LootTables.Entities.Enums;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.LootTables.Entities.EntityLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class OverworldPassive implements LootTableChanger, EntityLootTable {
    private final Ascension plugin;
    public OverworldPassive(Ascension plugin) {
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
            giveForagingExp(p, ForageBlocks.FORAGING_COMMON);

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }


    @Override
    public void registerItem() {
        addEntity(EntityType.SQUID.translationKey(),this);
        addEntity(EntityType.COD.translationKey(),this);
        addEntity(EntityType.SALMON.translationKey(),this);
        addEntity(EntityType.SQUID.translationKey(),this);
        addEntity(EntityType.TURTLE.translationKey(),this);
    }
}
