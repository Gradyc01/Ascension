package me.depickcator.ascension.LootTables.Blocks.Ores;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.LootTables.Blocks.BlockUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class CoalOre implements LootTableChanger, BlockLootTable {
    private final Ascension plugin;
    public CoalOre(Ascension plugin) {
        this.plugin = plugin;
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
                giveMiningExp(p, BlockUtil.MINING_COMMON);
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
