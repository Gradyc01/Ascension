package org.bingo4.LootTables.Blocks.Ores;

import org.bingo4.LootTables.Blocks.BlockLootTable;
import org.bingo4.LootTables.LootTableChanger;
//import org.bingo4.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class RedstoneOre implements LootTableChanger, BlockLootTable {
    public RedstoneOre() {
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
//                giveMiningExp(p, SkillExpAmount.MINING_RARE.getExp());
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.REDSTONE_ORE, this);
        addBlock(Material.DEEPSLATE_REDSTONE_ORE, this);
    }
}
