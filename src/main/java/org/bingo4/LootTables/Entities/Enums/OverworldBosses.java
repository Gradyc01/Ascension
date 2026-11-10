package org.bingo4.LootTables.Entities.Enums;

import org.bingo4.LootTables.Entities.EntityLootTable;
import org.bingo4.LootTables.LootTableChanger;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class OverworldBosses implements LootTableChanger, EntityLootTable {
    public OverworldBosses() {
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event event, Player p) {
        try {

        } catch (Exception ignored) {
            return false;
        }
        return true;
    }


    @Override
    public void registerItem() {
        addEntity(EntityType.EVOKER.translationKey(),this);
        addEntity(EntityType.ELDER_GUARDIAN.translationKey(),this);
        addEntity(EntityType.RAVAGER.translationKey(),this);
    }
}
