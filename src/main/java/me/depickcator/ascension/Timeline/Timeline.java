package me.depickcator.ascension.Timeline;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.MainMenuUI.Map.MapItem;
import me.depickcator.ascension.MainMenuUI.Map.MapItems;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Player.Data.Scoreboards.GameBoard;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
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

public abstract class Timeline {
    protected final Ascension plugin;
    private int MINUTES;
    private int SECONDS;
    private boolean keepRunning;
    private AscensionEvent ascensionEvent;
    private Scavenger scavenger;
    private BukkitTask timeline;
    private final int STARTING_MINUTES;
    private MapItems mapItems;
    private List<Pair<String, Integer>> nextBigEvent;
    public Timeline(int startingMinutes) {
        this.plugin = Ascension.getInstance();
        STARTING_MINUTES = startingMinutes;
        this.MINUTES = STARTING_MINUTES;
        mapItems = new MapItems();
    }

    public void resetTimeline() {
        keepRunning = false;
        MINUTES = STARTING_MINUTES;
        nextBigEvent = initNextBigEvents();
//        nextBigEvent = new ArrayList<>(List.of(
//                new MutablePair<>("Grace Period Ends", 20),
//                new MutablePair<>("Feast", 80),
//                new MutablePair<>("Final Ascension", STARTING_MINUTES)
//        ));
        removeAscensionElements();
        mapItems = new MapItems();
        plugin.getSettingsUI().
                getSettings()
                .getTimeline().
                getMapItems().
                addMapItem(new MapItem("Spawn", Ascension.getSpawn().getBlockX(), Ascension.getSpawn().getBlockZ(), MapItem.SPAWN));
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

    protected abstract void checkForMidGameEvents();
    protected abstract List<Pair<String, Integer>> initNextBigEvents();
    protected int getFinalAscensionBorderShrinkSize() {
        return 1000;
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
                if (getTimeTillNextBigEvent() == 1) {
                    mainTimelineSeconds();
                }
                if (MINUTES > 0) {
                    MINUTES--;
                } else {
                    cancel();
                    TextUtil.debugText("Timeline Ended");
                    new FinalAscension(getFinalAscensionBorderShrinkSize());
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
                if ((SECONDS % 30 == 0 || SECONDS <= 5) && SECONDS !=0) {
                    TextUtil.broadcastMessage(TextUtil.makeText(getNextBigEvent().getLeft() + " in " + SECONDS + " seconds", TextUtil.YELLOW));
                    SoundUtil.broadcastSound(Sound.UI_BUTTON_CLICK, 100, 1);
                }
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


    public Component getTime() {
        if (getTimeTillNextBigEvent() > 1) {
            return TextUtil.makeText("    " + (getTimeTillNextBigEvent()) + " Minutes", TextUtil.WHITE);
        } else {
            return TextUtil.makeText("    " + SECONDS + " Seconds", TextUtil.WHITE);
        }
    }

    private void removeAscensionElements() {
        if (ascensionEvent != null) {
            ascensionEvent.clear();
        }
    }

    public void setTime(int time) {
        MINUTES = time;
        TextUtil.debugText("Set timeline to " + MINUTES + " minutes");
        List<Pair<String, Integer>> events = new ArrayList<>(nextBigEvent);
        for (Pair<String, Integer> event : events) {
            if (event.getRight() <= STARTING_MINUTES - MINUTES) {
                nextBigEvent.remove(event);
            }
        }
    }

    public void updatePlayers() {
        for (PlayerData p: PlayerUtil.getAllPlayingPlayers()) {
            GameBoard b = (GameBoard) p.getPlayerScoreboard().getBoards();
//            p.getPlayerScoreboard().update();
//            b.update();
            b.updateTime();
        }
    }

    public AscensionEvent getAscensionEvent() {
        return ascensionEvent;
    }

    public Scavenger getScavenger() {
        return scavenger;
    }

    protected void setScavenger(Scavenger scavenger) {
        this.scavenger = scavenger;
    }

    protected void setAscensionEvent(AscensionEvent ascensionEvent) {
        this.ascensionEvent = ascensionEvent;
    }

    public int getStartingMinutes() {
        return STARTING_MINUTES;
    }

    public MapItems getMapItems() {
        return mapItems;
    }

    public Pair<String, Integer> getNextBigEvent() {
        if (nextBigEvent.isEmpty()) {
            return new MutablePair<>("Final Ascension", STARTING_MINUTES);
        }
        return nextBigEvent.getFirst();
    }

    public int getTimePassed() {
        return STARTING_MINUTES - MINUTES;
    }

    public int getTimeTillNextBigEvent() {
        return MINUTES - (STARTING_MINUTES - getNextBigEvent().getRight());
    }
}
