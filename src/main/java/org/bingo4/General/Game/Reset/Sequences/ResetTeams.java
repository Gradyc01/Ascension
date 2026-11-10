package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Teams.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
