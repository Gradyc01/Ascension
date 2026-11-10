package org.bingo4.General.Game.Relaunch.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KickPlayers extends GameSequences {

    @Override
    public void run(GameLauncher game) {
        for (Player p :Bukkit.getOnlinePlayers()) {
            p.kick(TextUtil.makeText("Server is Restarting! Join back in a minute", TextUtil.GOLD));
        }
        game.callback();
    }
}
