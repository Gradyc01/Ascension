package me.depickcator.ascensionBingo.LootTables.Entities.Enums;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascensionBingo.LootTables.Entities.EntityLootTable;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class OverworldPassive implements LootTableChanger, EntityLootTable {
    private final AscensionBingo plugin;
    public OverworldPassive(AscensionBingo plugin) {
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
