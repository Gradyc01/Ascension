package me.depickcator.ascension.LootTables.Blocks.Ores;

import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class EmeraldOre implements LootTableChanger, BlockLootTable {
    public EmeraldOre() {
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
                giveMiningExp(p, SkillExpAmount.MINING_LEGENDARY.getExp());
            } catch (Exception ignored) {}
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.EMERALD_ORE, this);
        addBlock(Material.DEEPSLATE_EMERALD_ORE, this);
    }
}
