package me.depickcator.ascension.LootTables.Blocks.Ores;

import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class GoldOre implements LootTableChanger, BlockLootTable {
    public GoldOre() {
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
                giveMiningExp(p, SkillExpAmount.MINING_UNCOMMON.getExp());
                if (e instanceof BlockBreakEvent) {
                    if (new Random().nextInt(0, 2) == 1) {
                        Block b = ((BlockBreakEvent) e).getBlock();
                        b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.RAW_GOLD));
                    }
                }
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.GOLD_ORE, this);
        addBlock(Material.DEEPSLATE_GOLD_ORE, this);
    }
}
