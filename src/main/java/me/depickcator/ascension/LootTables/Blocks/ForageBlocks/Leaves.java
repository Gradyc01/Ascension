package me.depickcator.ascension.LootTables.Blocks.ForageBlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class Leaves implements LootTableChanger, BlockLootTable, ForageBlocks {

    private final Ascension plugin;
    public Leaves() {
        this.plugin = Ascension.getInstance();
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        BlockBreakEvent event = (BlockBreakEvent) e;
        ItemStack mainHandItem = event.getPlayer().getInventory().getItemInMainHand();
        if (!event.getBlock().hasMetadata(PLACED_BY_PLAYER)
                && mainHandItem.getType() == Material.SHEARS
                && !mainHandItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            Random r = new Random();
            if (r.nextInt(0, 20) == 1) {
                event.setDropItems(false);
                p.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.OAK_LEAVES, this);
        addBlock(Material.SPRUCE_LEAVES, this);
        addBlock(Material.CHERRY_LEAVES, this);
        addBlock(Material.BIRCH_LEAVES, this);
        addBlock(Material.DARK_OAK_LEAVES, this);
        addBlock(Material.JUNGLE_LEAVES, this);
        addBlock(Material.ACACIA_LEAVES, this);
    }

    @Override
    public void onPlacedForagingBlock(BlockPlaceEvent event) {
        event.getBlock().setMetadata(PLACED_BY_PLAYER, new FixedMetadataValue(plugin, true));
    }
}
