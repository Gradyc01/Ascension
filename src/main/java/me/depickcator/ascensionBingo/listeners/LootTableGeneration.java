package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.Items.Craftable.Unlocks.KingsRod;
import me.depickcator.ascensionBingo.LootTables.LootTableChanger;
import me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks.ForageBlocks;
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
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class LootTableGeneration implements Listener {
    private AscensionBingo plugin;

    public LootTableGeneration(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getGameState().canNotBuild()) {
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
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == KingsRod.MODEL_NUMBER) {
                plugin.getWorld().dropItemNaturally(event.getPlayer().getLocation(), new ItemStack(Material.GOLD_NUGGET, 12));
            }
        }
    }
}
