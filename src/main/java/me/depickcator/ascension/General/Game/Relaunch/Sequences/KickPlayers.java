package me.depickcator.ascension.General.Game.Relaunch.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
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
