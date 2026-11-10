package org.bingo4.Timeline.Events.GracePeriod;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Teams.Team;
import org.bingo4.Teams.TeamUtil;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Sound;

public class GracePeriodEnds {
    public GracePeriodEnds() {
        Bingo4 plugin = Bingo4.getInstance();
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
//        for (Team t: TeamUtil.getEveryTeam(true)) {
//            t.getTeamBackpack().shutdownBackpack();
//        };
    }
}
