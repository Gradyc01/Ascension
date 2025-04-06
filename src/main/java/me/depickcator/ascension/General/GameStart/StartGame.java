package me.depickcator.ascension.General.GameStart;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StartGame {
    private final Ascension plugin;
    private final List<GameStartSequence> sequence;
    private final Settings settings;
    public StartGame() {
        plugin = Ascension.getInstance();
        settings = plugin.getSettingsUI().getSettings();
        sequence = new ArrayList<>(settings.getSequence());
        start();
    }

    public void start() {
        if (plugin.getLocationCheck().isCheckCompleted()) {
            plugin.getGameState().setCurrentState(GameStates.GAME_LOADING);
            loop();
        } else {
            TextUtil.broadcastMessage(TextUtil.makeText("Failed to Start Game: Location Check is incomplete", TextUtil.DARK_RED));
            SoundUtil.broadcastSound(Sound.ENTITY_PLAYER_TELEPORT, 10f, 0);
        }
    }

    private void loop() {
        if (sequence.isEmpty()) {
            end();
            return;
        }
        GameStartSequence seq = sequence.getFirst();
        seq.run(this);
        TextUtil.debugText("StartGame: Ran Next Sequence");
        sequence.removeFirst();
    }

    private void end() {
        plugin.getGameState().setCurrentState(GameStates.GAME_BEFORE_GRACE);
        settings.getTimeline().startTimeline();
        int gracePeriodDuration = settings.getTimeline().getNextBigEvent().getRight();
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            pD.resetAfterStartGame(gracePeriodDuration);

        }
    }

    /* What each GameStartSequence should call at the end of their run */
    public void callback() {
        new BukkitRunnable() {
            public void run() {
                loop();
            }
        }.runTaskLater(Ascension.getInstance(), 40);
    }


}
