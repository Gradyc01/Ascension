package me.depickcator.ascensionBingo.LootTables.Blocks.Ores;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.LootTableChanger;
import me.depickcator.ascensionBingo.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascensionBingo.LootTables.Blocks.BlockUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class NetherGoldOre implements LootTableChanger, BlockLootTable {
    private final AscensionBingo plugin;
    public NetherGoldOre(AscensionBingo plugin) {
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
                giveMiningExp(p, BlockUtil.MINING_RARE);
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.NETHER_GOLD_ORE, this);
    }
}
