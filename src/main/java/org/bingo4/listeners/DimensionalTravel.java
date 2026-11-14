package org.bingo4.listeners;

import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class DimensionalTravel implements Listener {
    @EventHandler
    public void onPlayerTravel(PlayerPortalEvent event) {
        String debugText = "[Dimensional Travel] ";
        Bingo4 plugin = Bingo4.getInstance();
        Player player = event.getPlayer();

        Location newLocation = event.getTo().clone();
        debugText+=player.getName() + " traveling from " + player.getWorld().getName() + " to" + newLocation.getWorld().getName();
        if (player.getWorld().equals(plugin.getNether())) {
            newLocation.setWorld(plugin.getWorld());
        } else {
            newLocation.setWorld(plugin.getNether());
        }
        debugText+=" redirected to" + newLocation.getWorld().getName();
        TextUtil.debugText(debugText);
        event.setTo(newLocation);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (e.isNewChunk()) {
            TextUtil.broadcastMessage(TextUtil.makeText("NEW CHUNK GENERATED"));
        }
    }
}
