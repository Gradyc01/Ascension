package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class DimensionalTravel implements Listener {
    @EventHandler
    public void onPlayerTravel(PlayerPortalEvent event) {
        String debugText = "[Dimensional Travel] ";
        Ascension plugin = Ascension.getInstance();
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
}
