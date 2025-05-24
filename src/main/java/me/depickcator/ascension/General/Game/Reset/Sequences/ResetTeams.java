package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Teams.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ResetTeams extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        resetTeams();

        game.callback(10);
    }

    private void resetTeams() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                TeamUtil.disbandTeam(p);
            } catch (Exception ignored) {
                continue;
            }
        }
    }
}
