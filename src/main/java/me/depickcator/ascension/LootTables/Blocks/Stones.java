package me.depickcator.ascension.LootTables.Blocks;

import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Stones implements LootTableChanger, BlockLootTable {
    public Stones() {
        registerItem();
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        BlockBreakEvent event = (BlockBreakEvent) e;
        event.setDropItems(false);
        p.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
        return true;
    }

    @Override
    public void registerItem() {
        addBlock(Material.ANDESITE, this);
        addBlock(Material.DIORITE, this);
        addBlock(Material.GRANITE, this);
    }
}
