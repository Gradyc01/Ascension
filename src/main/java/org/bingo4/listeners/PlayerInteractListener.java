package org.bingo4.listeners;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Interfaces.AscensionBlockGUI;
import org.bingo4.Interfaces.BlockUI;
import org.bingo4.Interfaces.EntityInteraction;
import org.bingo4.Interfaces.ItemClick;
import org.bingo4.Player.Cooldowns.EntityInteractionCooldown;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;

import java.util.Set;


public class PlayerInteractListener implements Listener {
     private final Bingo4 plugin;
     private final Set<Material> unRightClickables = Set.of(Material.CRAFTING_TABLE, Material.CARTOGRAPHY_TABLE);

    public PlayerInteractListener() {
         this.plugin = Bingo4.getInstance();
    }
    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (plugin.getGameState().checkState(GameStates.UNLOADED)) return;
        PlayerData pD = PlayerUtil.getPlayerData(p);

        boolean blockHasInventory = blockHasInventory(e, pD);
        if (e.getItem() == null) return;
        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        if (!blockHasInventory && itemClick != null && pD != null && !EntityInteractionCooldown.getInstance().isOnCooldown(p)) {
            itemClick.uponClick(e, pD);
            EntityInteractionCooldown.getInstance().setCooldownTimer(p);
        }
    }



//    /*Returns True if when the player interacts with a block with a Inventory
//    * returns False otherwise*/
//    private boolean blockHasInventory(PlayerInteractEvent e) {
//        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return false;
//        Block b = e.getClickedBlock();
//        if (b.getBlockData() instanceof Openable) return true;
//        if (unRightClickables.contains(b.getType())) return true;
//        return b.getState() instanceof InventoryHolder;
//    }

    /*Returns True if when the player interacts with a block with a Inventory
     * returns False otherwise*/
    private boolean blockHasInventory(PlayerInteractEvent e, PlayerData pD) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return false;
        Block b = e.getClickedBlock();
        BlockUI gui = AscensionBlockGUI.findGUI(b);
        if (gui != null) {
            e.setCancelled(!gui.interactWithBlock(pD, b, e));
            return true;
        }
        if (b.getBlockData() instanceof Openable) return true;
        if (unRightClickables.contains(b.getType())) return true;
        return b.getState() instanceof InventoryHolder;
    }

    @EventHandler
    public void interactingWithEntity(PlayerInteractEntityEvent e) {
//        if (!(e.getRightClicked() instanceof LivingEntity)) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
//        LivingEntity entity = (LivingEntity) e.getRightClicked();
        EntityInteraction entityInteraction = EntityInteraction.getEntityInteraction(e.getRightClicked());
        if (entityInteraction != null  && !EntityInteractionCooldown.getInstance().isOnCooldown(e.getPlayer())) {
            entityInteraction.interact(e);
            EntityInteractionCooldown.getInstance().setCooldownTimer(e.getPlayer());
            return;
        }
    }

}
