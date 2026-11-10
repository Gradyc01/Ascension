package org.bingo4.listeners;

import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAchievements implements Listener {
    public PlayerAchievements() {
    }

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {

        Component text = event.message();
        if (text != null) {
            event.getPlayer().sendMessage(text.color(TextUtil.RED));
            event.message(null);
        }
    }
}
