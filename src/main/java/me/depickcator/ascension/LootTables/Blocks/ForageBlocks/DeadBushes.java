package me.depickcator.ascension.LootTables.Blocks.ForageBlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Blocks.BlockLootTable;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Skills.SkillExpAmount;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class DeadBushes implements LootTableChanger, BlockLootTable, ForageBlocks {

    private final Ascension plugin;
    public DeadBushes() {
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
        ItemStack mainHandItem = p.getInventory().getItemInMainHand();
        if (!event.getBlock().hasMetadata(PLACED_BY_PLAYER)
                && mainHandItem.getType() != Material.SHEARS
                && (mainHandItem.getType() == Material.AIR ||
                !mainHandItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) {
            PlayerSkills playerSkills = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerSkills();
            playerSkills.getForaging().addExp(SkillExpAmount.FORAGING_COMMON.getExp());
            Location loc = event.getBlock().getLocation();
            loc.getWorld().dropItem(loc, new ItemStack(Material.OAK_PLANKS, 4));
            return true;
        }
        return false;
    }

    @Override
    public void registerItem() {
        addBlock(Material.DEAD_BUSH, this);
        addBlock(Material.SHORT_DRY_GRASS, this);
        addBlock(Material.TALL_DRY_GRASS, this);
    }

    @Override
    public void onPlacedForagingBlock(BlockPlaceEvent event) {
        event.getBlock().setMetadata(PLACED_BY_PLAYER, new FixedMetadataValue(plugin, true));
    }
}
