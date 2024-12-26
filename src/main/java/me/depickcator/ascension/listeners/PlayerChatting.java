package me.depickcator.ascension.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerChatting implements Listener {
    private final Ascension plugin;
    public PlayerChatting() {
        this.plugin = Ascension.getInstance();
    }
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
//        TextUtil.debugText(event.getPlayer().getName() + "Send a chat message?");
        Player p = event.getPlayer();
//        event.message();
        event.setCancelled(true);
        Component playerName = TextUtil.makeText(p.getName() + ": ", TextUtil.DARK_GRAY);
        Component message = event.message().color(TextUtil.GRAY);
        if (plugin.getGameState().inGame()) {
            Component teamTag = TextUtil.makeText("[TEAM] ", TextUtil.BLUE);
            List<Player> playerList = PlayerUtil.getPlayerData(p).getPlayerTeam().getTeam().getTeamMembers();
            TextUtil.broadcastMessage(teamTag.append(playerName).append(message), playerList);
        } else {
            TextUtil.broadcastMessage(playerName.append(message));
        }

    }
}
