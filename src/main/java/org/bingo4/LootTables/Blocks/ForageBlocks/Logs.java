package org.bingo4.LootTables.Blocks.ForageBlocks;

import org.bingo4.Bingo4;
import org.bingo4.LootTables.Blocks.BlockLootTable;
import org.bingo4.LootTables.LootTableChanger;
//import org.bingo4.Player.Data.PlayerSkills;
import org.bingo4.Player.Data.PlayerUtil;
//import org.bingo4.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class Logs implements LootTableChanger, BlockLootTable, ForageBlocks {

    private final Bingo4 plugin;
    public Logs() {
        this.plugin = Bingo4.getInstance();
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        BlockBreakEvent event = (BlockBreakEvent) e;
        if (!event.getBlock().hasMetadata(PLACED_BY_PLAYER)) {
//            PlayerSkills playerSkills = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerSkills();
//            playerSkills.getForaging().addExp(SkillExpAmount.FORAGING_COMMON.getExp());
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.OAK_LOG, this);
        addBlock(Material.SPRUCE_LOG, this);
        addBlock(Material.CHERRY_LOG, this);
        addBlock(Material.BIRCH_LOG, this);
        addBlock(Material.DARK_OAK_LOG, this);
        addBlock(Material.JUNGLE_LOG, this);
        addBlock(Material.ACACIA_LOG, this);
        addBlock(Material.SUGAR_CANE, this);
        addBlock(Material.BROWN_MUSHROOM, this);
        addBlock(Material.RED_MUSHROOM, this);
    }

    @Override
    public void onPlacedForagingBlock(BlockPlaceEvent event) {
        event.getBlock().setMetadata(PLACED_BY_PLAYER, new FixedMetadataValue(plugin, true));
    }
}
