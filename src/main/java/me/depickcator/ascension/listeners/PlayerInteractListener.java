package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashSet;
import java.util.Set;


public class PlayerInteractListener implements Listener {
     private final Ascension plugin;
     private final Set<Material> unRightClickables = Set.of(Material.CRAFTING_TABLE, Material.CARTOGRAPHY_TABLE);

    public PlayerInteractListener() {
         this.plugin = Ascension.getInstance();
    }
    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (plugin.getGameState().checkState(GameStates.UNLOADED)) return;
        if (e.getItem() == null) return;
        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (itemClick != null && pD != null && !blockHasInventory(e)) {
            itemClick.uponClick(e, pD);
        }
    }

    /*Returns True if when the player interacts with a block with a Inventory
    * returns False otherwise*/
    private boolean blockHasInventory(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return false;
        Block b = e.getClickedBlock();
        if (b.getBlockData() instanceof Openable) return true;
        if (unRightClickables.contains(b.getType())) return true;
        return b.getState() instanceof InventoryHolder;
    }

    @EventHandler
    public void interactingWithEntity(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof LivingEntity)) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        LivingEntity entity = (LivingEntity) e.getRightClicked();
        EntityInteraction entityInteraction = EntityInteraction.getEntityInteraction(entity);
        if (entityInteraction != null) {
            entityInteraction.interact(e);
            return;
        }
    }

}
