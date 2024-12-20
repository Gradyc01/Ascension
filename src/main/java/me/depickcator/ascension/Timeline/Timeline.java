package me.depickcator.ascension.Timeline;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.FinalAscension.FinalAscension;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class Timeline {
    private final Ascension plugin;
    private int MINUTES;
    private int SECONDS;
    private boolean keepRunning;
    private Scavenger scavenger;
    private AscensionEvent ascensionEvent;
    private static final int STARTING_MINUTES = 160;
    public Timeline(Ascension plugin) {
        this.plugin = plugin;
        this.MINUTES = STARTING_MINUTES;
    }

    public void startTimeline() {
        keepRunning = true;
        mainTimelineMinutes();
        TextUtil.debugText("Started Timeline");
    }

    public void pauseTimeline() {
        keepRunning = false;
        TextUtil.debugText("Paused Timeline");
    }

    private void mainTimelineMinutes() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    TextUtil.debugText("Timeline Forcefully Stopped");
                    return;
                }

                checkForMidGameEvents();
                updatePlayers();

                if (MINUTES > 1) {
                    MINUTES--;
                } else {
                    cancel();
                    TextUtil.debugText("Timeline Ended");
                    mainTimelineSeconds();
                    return;
                }
                TextUtil.debugText("Timeline Ran");

            }
        }.runTaskTimer(plugin, 0, 60 * 20);
    }

    private void mainTimelineSeconds() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    TextUtil.debugText("Timeline Forcefully Stopped");
                    return;
                }

                updatePlayers();

                if (SECONDS > 1) {
                    SECONDS--;
                } else {
                    cancel();
                    TextUtil.debugText("Timeline Ended");
                    new FinalAscension();
                    return;
                }
                TextUtil.debugText("Timeline Ran");

            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private void checkForMidGameEvents() {
        int timePassed = STARTING_MINUTES - MINUTES;
        switch (timePassed) {
            case 5 -> {
                scavenger = new Scavenger();
                scavenger.announceTrades();
            }
            case 30 -> {
                plugin.getGameState().setCurrentState(GameStates.GAME_AFTER_GRACE);
                plugin.getServer().broadcast(TextUtil.makeText("Grace Period has Ended", TextUtil.BLUE));
                SoundUtil.broadcastSound(Sound.ENTITY_WITHER_DEATH, 30, 1);
            }
            case 31 -> {
                ascensionEvent = new AscensionEvent();
            }
            case 35, 70, 100, 140 -> {
                new CarePackage();
            }
            case 50, 115 -> {
                //Scavenger Spawn Location
                scavenger.announceSpawnLocation();
            }
            case 55, 120 -> {
                //Scavenger Spawn In
                scavenger.spawnInScavenger();
            }
            case 90 -> {
                //Feast
                new Feast();
                TextUtil.debugText("Feast");
            }

        }
    }

    public Component getTime() {
        if (MINUTES > 1) {
            return TextUtil.makeText("    " + MINUTES + " Minutes", TextUtil.WHITE);
        } else {
            return TextUtil.makeText("    " + SECONDS + " Seconds", TextUtil.WHITE);
        }
    }

    public void resetTimeline() {
        keepRunning = false;
        MINUTES = STARTING_MINUTES;
        SECONDS = 60;
        scavenger = null;
    }

    public void setTime(int time) {
        MINUTES = time;
    }

    public void updatePlayers() {
        for (PlayerData p: Ascension.playerDataMap.values()) {
            p.getPlayerScoreboard().updateGameBoard(true);
        }
    }

    public Scavenger getScavenger() {
        return scavenger;
    }

    public AscensionEvent getAscensionEvent() {
        return ascensionEvent;
    }
}
