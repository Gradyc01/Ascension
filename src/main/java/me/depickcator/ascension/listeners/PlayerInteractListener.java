package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Interfaces.ItemClick;
import me.depickcator.ascension.Interfaces.EntityInteraction;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteractListener implements Listener {
     private final Ascension plugin;

    public PlayerInteractListener() {
         this.plugin = Ascension.getInstance();
    }
    @EventHandler
    public void mainMenuClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (plugin.getGameState().checkState(GameStates.UNLOADED)) return;
        if (e.getItem() == null) return;
        ItemClick itemClick = ItemClick.findClickItem(e.getItem());
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (itemClick != null && pD != null) {
            itemClick.uponClick(e, pD);
        }
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
