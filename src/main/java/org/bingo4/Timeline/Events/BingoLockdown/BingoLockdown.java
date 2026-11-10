package org.bingo4.Timeline.Events.BingoLockdown;

import net.kyori.adventure.text.Component;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Teams.Team;
import org.bingo4.Teams.TeamBingoLockdown;
import org.bingo4.Timeline.Events.Winner.Winner;
import org.bingo4.Timeline.Timeline;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BingoLockdown {

    private final Bingo4 plugin = Bingo4.getInstance();
    private boolean eventOngoing;
    private Timeline timeline;
    private Team team;

    public BingoLockdown() {
        eventOngoing = false;
    }

    public boolean canStartEvent(Team team) {
        TextUtil.debugText("Ran Bingo4 Check");
        boolean canBeginAscension = team.getTeamStats().isBingoLockdown();
        if (!eventOngoing && canBeginAscension && !plugin.getGameState().checkState(GameStates.GAME_ENDING)) {
            return true;
        } else {
            return false;
        }
    }

    public void start(Team team) {
        this.team = team;
        eventOngoing = true;
        plugin.getGameState().setCurrentState(GameStates.GAME_ASCENSION);
        timeline = plugin.getSettingsUI().getSettings().getTimeline();
        team.getTeamBingoLockdown().startAscension();
        startText(team);
        loop();
    }

    private void stop() {
        eventOngoing = false;
        team = null;
    }

    private void loop() {
        new BukkitRunnable() {
            TeamBingoLockdown teamBL = team.getTeamBingoLockdown();
            @Override
            public void run() {
                if (!eventOngoing || !team.getTeamStats().isBingoLockdown() || !plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
                    failed();
                    cancel();
                    return;
                }
                if (teamBL.getAscensionTimer() <= 0) {
                    success();
                    cancel();
                    return;
                }
                teamBL.addAscensionTimer(-1);
                timeline.updatePlayers();
            }
        }.runTaskTimer(Bingo4.getInstance(), 0, 20);
    }

    public void success() {
        new Winner(new ArrayList<>(List.of(team)));
        successText();
        stop();
        TextUtil.debugText("Bingo4 Success");
    }

    public void failed() {
        plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
        failedText();
        team.getTeamBingoLockdown().failedAscension();
        stop();
        timeline.updatePlayers();
        TextUtil.debugText("Bingo4 Failed");
    }

    private void failedText() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n            ASCENSION DENIED\n", TextUtil.WHITE, true, false));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 100, 1);
    }

    private void successText() {
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n            ASCENSION COMPLETE\n", TextUtil.WHITE, true, false));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        TextUtil.broadcastMessage(text);
        SoundUtil.broadcastSound(Sound.ENTITY_ENDER_DRAGON_DEATH, 100, 1);
    }

        private void startText(Team team) {
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n        BINGO LOCKDOWN INITIATED\n", TextUtil.WHITE));
        text = text.append(TextUtil.makeText("    " + team.getLeader().getName() + "'s Team has locked down the board", TextUtil.DARK_GRAY));
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        plugin.getServer().broadcast(text);
        TextUtil.broadcastTitle(TextUtil.makeTitle(TextUtil.makeText("BINGO LOCKDOWN", TextUtil.YELLOW), 3, 5, 2));
        SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 20f, 0.7);
    }

    public Team getTeam() {
        return team;
    }
}
