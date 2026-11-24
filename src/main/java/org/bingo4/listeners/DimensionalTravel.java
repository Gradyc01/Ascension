package org.bingo4.listeners;

import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Timeline.Events.NetherManager.NetherManager;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.logging.Level;

public class DimensionalTravel implements Listener {
    @EventHandler
    public void onPlayerTravel(PlayerPortalEvent event) {
        String debugText = "[Dimensional Travel] ";
        Bingo4 plugin = Bingo4.getInstance();
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData == null) return;

        if (!plugin.getGameState().canDimensionalTravel(player)) {
            event.setCancelled(true);
        }

        Location newLocation = event.getTo().clone();
        debugText+=player.getName() + " traveling from " + player.getWorld().getName() + " to" + newLocation.getWorld().getName();
        NetherManager netherManager = plugin.getSettingsUI().getSettings().getTimeline().getNetherManager();
        if (player.getWorld().equals(plugin.getNether())) {
            newLocation.setWorld(plugin.getWorld());
            playerData.getPlayerScoreboard().makeGameBoard();
            netherManager.removePlayerData(playerData);
        } else {
            newLocation.setWorld(plugin.getNether());
            playerData.getPlayerScoreboard().makeNetherBoard(netherManager);
            netherManager.addPlayerData(playerData);
        }
        debugText+=" redirected to" + newLocation.getWorld().getName();
        TextUtil.debugText(debugText);
        event.setTo(newLocation);
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getReason() == PortalCreateEvent.CreateReason.FIRE && event.getWorld().equals(Bingo4.getInstance().getWorld())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (e.isNewChunk()) {
            TextUtil.debugText(String.format("New chunk generated in world type %s", e.getWorld().getWorldPath()), Level.FINE);
        }
    }
}
