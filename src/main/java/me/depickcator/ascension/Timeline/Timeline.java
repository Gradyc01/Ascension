package me.depickcator.ascension.Timeline;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.MainMenu.Map.MapItem;
import me.depickcator.ascension.MainMenu.Map.MapItems;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.Scoreboards.GameBoard;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.FinalAscension.FinalAscension;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
    private final Ascension plugin;
    private int MINUTES;
    private int SECONDS;
    private boolean keepRunning;
    private Scavenger scavenger;
    private AscensionEvent ascensionEvent;
    private BukkitTask timeline;
    private static final int STARTING_MINUTES = 160;
    private MapItems mapItems;
    private List<Pair<String, Integer>> nextBigEvent;
    public Timeline(Ascension plugin) {
        this.plugin = plugin;
        this.MINUTES = STARTING_MINUTES;
        mapItems = new MapItems();
        initNextBigEvent();
    }

    private void initNextBigEvent() {
        nextBigEvent = new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 130),
                new MutablePair<>("Feast", 70),
                new MutablePair<>("Final Ascension", 0)
        ));
    }

    public void startTimeline() {
        keepRunning = true;
        mainTimelineMinutes();
        TextUtil.debugText("Started Timeline");
    }

    public void pauseTimeline() {
        keepRunning = false;
        timeline.cancel();
        TextUtil.debugText("Paused Timeline");
    }

    private void mainTimelineMinutes() {
        timeline = new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    TextUtil.debugText("Timeline Forcefully Stopped");
                    return;
                }

                checkForMidGameEvents();
                updatePlayers();
                if (MINUTES - nextBigEvent.getFirst().getRight() == 1) {
                    mainTimelineSeconds();
                }
                if (MINUTES > 0) {
                    MINUTES--;
                } else {
                    cancel();
                    TextUtil.debugText("Timeline Ended");
                    new FinalAscension();
                    return;
                }
                TextUtil.debugText("Timeline Ran (" + MINUTES + ")");

            }
        }.runTaskTimer(plugin, 0, 60 * 20);
    }

    private void mainTimelineSeconds() {
        SECONDS = 60;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!keepRunning) {
                    cancel();
                    TextUtil.debugText("Seconds Visual Timeline Forcefully Stopped");
                    return;
                }

                updatePlayers();

                if (SECONDS > 1) {
                    SECONDS--;
                } else {
                    cancel();
                    nextBigEvent.removeFirst();
                    TextUtil.debugText("Seconds Timeline Ended");
                    return;
                }
                TextUtil.debugText("Seconds Timeline Ran (" + SECONDS + ")");

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
//                nextBigEvent = new MutablePair<>("Feast", 90);
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
//                nextBigEvent = new MutablePair<>("Final Ascension", 160);
            }
            case 90 -> {
                //Feast
                new Feast();
                TextUtil.debugText("Feast");
            }

        }
    }

    public Component getTime() {
        if (MINUTES - getNextBigEvent().getRight() > 1) {
            return TextUtil.makeText("    " + (MINUTES - getNextBigEvent().getRight()) + " Minutes", TextUtil.WHITE);
        } else {
            return TextUtil.makeText("    " + SECONDS + " Seconds", TextUtil.WHITE);
        }
    }

    public void resetTimeline() {
        keepRunning = false;
        MINUTES = STARTING_MINUTES;
        SECONDS = 60;
        scavenger = null;
//        nextBigEvent = new MutablePair<>("Grace Period Ends", 30);
        removeAscensionElements();
        mapItems = new MapItems();
        Ascension.getInstance().getTimeline().getMapItems().addMapItem(new MapItem("Spawn", Ascension.getSpawn().getBlockX(), Ascension.getSpawn().getBlockZ(), MapItem.SPAWN));
    }

    private void removeAscensionElements() {
        if (ascensionEvent != null) {
            ascensionEvent.clear();
        }
    }

    public void setTime(int time) {
        MINUTES = time;
    }

    public void updatePlayers() {
        for (PlayerData p: Ascension.playerDataMap.values()) {
            GameBoard b = (GameBoard) p.getPlayerScoreboard().getBoards();
//            p.getPlayerScoreboard().update();
//            b.update();
            b.updateTime();
        }
    }

    public Scavenger getScavenger() {
        return scavenger;
    }

    public AscensionEvent getAscensionEvent() {
        return ascensionEvent;
    }

    public MapItems getMapItems() {
        return mapItems;
    }

    public Pair<String, Integer> getNextBigEvent() {
        return nextBigEvent.getFirst();
    }
}
