package org.bingo4.LootTables.Blocks.Ores;

import org.bingo4.LootTables.Blocks.BlockLootTable;
import org.bingo4.LootTables.LootTableChanger;
//import org.bingo4.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class CoalOre implements LootTableChanger, BlockLootTable {
    public CoalOre() {
        registerItem();
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        if (eligibleForMiningExp(e, p)) {
            try {
//                giveMiningExp(p, SkillExpAmount.MINING_COMMON.getExp());
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.COAL_ORE, this);
        addBlock(Material.DEEPSLATE_COAL_ORE, this);
    }
}
