package me.depickcator.ascension.Timeline.Events.GracePeriod;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Sound;

public class GracePeriodEnds {
    public GracePeriodEnds() {
        Ascension plugin = Ascension.getInstance();
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
        for (Team t: TeamUtil.getEveryTeam(true)) {
            t.getTeamBackpack().shutdownBackpack();
        };
    }
}
