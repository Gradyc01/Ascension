package me.depickcator.ascensionBingo.LootTables.Blocks.Ores;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import me.depickcator.ascensionBingo.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascensionBingo.LootTables.Blocks.BlockUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class IronOre implements LootTableChanger, BlockLootTable {
    private final AscensionBingo plugin;
    public IronOre(AscensionBingo plugin) {
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
                giveMiningExp(p, BlockUtil.MINING_UNCOMMON);
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.IRON_ORE, this);
        addBlock(Material.DEEPSLATE_IRON_ORE, this);
    }
}
