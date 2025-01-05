package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAchievements implements Listener {
//    private final Ascension plugin;
    public PlayerAchievements() {
//        plugin = Ascension.getInstance();
    }

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {
        if (event.message() != null) {
            event.message(event.message().color(TextUtil.RED));
        }
//        for (String str : event.getAdvancement().getRequirements().getRequirements().getFirst().getRequiredCriteria()) {
//            TextUtil.debugText(str);
//        }
    }
}
