package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Unlocks.KingsRod;
import me.depickcator.ascension.LootTables.LootTableChanger;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootTableGeneration implements Listener {
    private final Ascension plugin;

    public LootTableGeneration() {
        this.plugin = Ascension.getInstance();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getGameState().canNotBuild() || event.getBlock().hasMetadata("UNBREAKABLE")) {
            event.setCancelled(true);
            return;
        }
        Player p = event.getPlayer();
        Block b = event.getBlock();

        LootTableChanger lootTableChanger = LootTableChanger.findItem(p.getInventory().getItemInMainHand());
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
            return;
        }
        lootTableChanger = LootTableChanger.findBlock(b);
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block b = event.getBlock();
        LootTableChanger lootTableChanger = LootTableChanger.findBlock(b);
        if (lootTableChanger != null) {
            if (lootTableChanger instanceof ForageBlocks) {
                ForageBlocks forageBlocks = (ForageBlocks) lootTableChanger;
                forageBlocks.onPlacedForagingBlock(event);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event) {
        List<Block> explodedBlocks = event.blockList();
        for (Block b: explodedBlocks) {
            if (b.hasMetadata("UNBREAKABLE")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getDamageSource().getCausingEntity();
        if (e == null || e.getType() != EntityType.PLAYER) {
            return;
        }
        Player p = (Player) e;
        LootTableChanger lootTableChanger = LootTableChanger.findEntity(event.getEntity());
        if (lootTableChanger != null) {
            lootTableChanger.uponEvent(event, p);
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == KingsRod.getInstance().getResult().getItemMeta().getCustomModelData()) {
                plugin.getWorld().dropItemNaturally(event.getPlayer().getLocation(), new ItemStack(Material.GOLD_NUGGET, 12));
            }
        }
    }
}
