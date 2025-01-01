package me.depickcator.ascension.LootTables.Blocks.ForageBlocks;

import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Aging implements LootTableChanger, BlockLootTable, ForageBlocks {

    public Aging() {
        registerItem();
    }
    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public boolean uponEvent(Event e, Player p) {
        BlockBreakEvent event = (BlockBreakEvent) e;
        Ageable ageable = (Ageable) event.getBlock().getBlockData();
        if (ageable.getAge() == ageable.getMaximumAge()) {
            PlayerSkills playerSkills = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerSkills();
            playerSkills.getForaging().addExp(SkillExpAmount.FORAGING_COMMON.getExp());
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.WHEAT, this);
        addBlock(Material.CARROTS, this);
        addBlock(Material.POTATOES, this);
        addBlock(Material.BEETROOTS, this);
        addBlock(Material.COCOA, this);
    }

    @Override
    public void onPlacedForagingBlock(BlockPlaceEvent event) {
        //Nothing
    }
}
