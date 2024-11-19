package me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.LootTableChanger;
import me.depickcator.ascensionBingo.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascensionBingo.Player.PlayerSkills;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class Logs implements LootTableChanger, BlockLootTable, ForageBlocks {

    private final AscensionBingo plugin;
    public Logs(AscensionBingo plugin) {
        this.plugin = plugin;
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
            PlayerSkills playerSkills = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerSkills();
            playerSkills.getForaging().addExp(FORAGING_COMMON);
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
    }

    @Override
    public void onPlacedForagingBlock(BlockPlaceEvent event) {
        event.getBlock().setMetadata(PLACED_BY_PLAYER, new FixedMetadataValue(plugin, true));
    }
}
