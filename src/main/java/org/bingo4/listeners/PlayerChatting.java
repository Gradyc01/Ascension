package org.bingo4.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerChatting implements Listener {
    private final Bingo4 plugin;
    public PlayerChatting() {
        this.plugin = Bingo4.getInstance();
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
