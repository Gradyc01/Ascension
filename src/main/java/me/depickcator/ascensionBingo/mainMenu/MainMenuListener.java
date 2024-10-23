package me.depickcator.ascensionBingo.mainMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class MainMenuListener implements Listener {
    @EventHandler
    public void mainMenuClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().toString().contains("RIGHT_CLICK") && Objects.equals(e.getItem(), GiveMainMenuItem.getItem())) {
            p.performCommand("open-main-menu");
        } else {
//            e.setCancelled(true); //TODO: Having this on turns off all interactions could be useful in the future
        }

    }
}
