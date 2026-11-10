package org.bingo4.Timeline;

import org.bingo4.Bingo4;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.MainMenuUI.Map.MapItems;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Player.Data.Scoreboards.GameBoard;
import org.bingo4.Structures.BingoTurnIn.BingoTurnInCamp;
import org.bingo4.Structures.CampManager;
import org.bingo4.Structures.MiningNode.LootTables.MiningNodeCampTier1;
import org.bingo4.Structures.MiningNode.LootTables.MiningNodeCampTier2;
import org.bingo4.Structures.MiningNode.LootTables.MiningNodeCampTier3;
import org.bingo4.Structures.MiningNode.MiningNodeCamp;
import org.bingo4.Structures.Structures;
import org.bingo4.Teams.Team;
//import org.bingo4.Timeline.Events.Ascension.AscensionEvent;
//import org.bingo4.Timeline.Events.FinalAscension.FinalAscension;
//import org.bingo4.Timeline.Events.Scavenger.Scavenger;
//import org.bingo4.Timeline.Events.SoulShop.SoulShops;
//import org.bingo4.Timeline.Events.Vaporization.VaporizationChecks;
import org.bingo4.Timeline.Events.BingoLockdown.BingoLockdown;
import org.bingo4.Utility.SchematicPlacer;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

import static org.bingo4.Structures.Structures.MINING_CAMP_1;

public abstract class Timeline {
    protected final Bingo4 plugin;
    private int MINUTES;
    private int SECONDS;
    private boolean keepRunning;
    private final BingoLockdown bingoLockdown;
//    private Scavenger scavenger;
//    private final SoulShops soulShops;
    private BukkitTask timeline;
    private final PeriodicChecks periodicChecks;
    private final int STARTING_MINUTES;
    private MapItems mapItems;
//    protected final VaporizationChecks vaporizationChecks;
    private CampManager campManager;
    private List<Pair<String, Integer>> nextBigEvent;
    private int gameScoreThreshold; // The bottom standard
    public Timeline(int startingMinutes) {
        this.plugin = Bingo4.getInstance();
        STARTING_MINUTES = startingMinutes;
        this.MINUTES = STARTING_MINUTES;
        mapItems = new MapItems();
        periodicChecks = new PeriodicChecks();
//        vaporizationChecks = new VaporizationChecks(this);
//        soulShops = new SoulShops();
        bingoLockdown = new BingoLockdown();
        campManager = new CampManager();
        try {
            campManager.registerCampType(new BingoTurnInCamp(plugin, new SchematicPlacer(), 0, 0));
            campManager.registerCampType(new MiningNodeCamp(new MiningNodeCampTier1()));
            campManager.registerCampType(new MiningNodeCamp(new MiningNodeCampTier2()));
            campManager.registerCampType(new MiningNodeCamp(new MiningNodeCampTier3()));
        } catch (Exception e) {
            TextUtil.debugText("SAKDLASKD");
        }

    }

    public void resetTimeline() {
        keepRunning = false;
        MINUTES = STARTING_MINUTES;
        nextBigEvent = initNextBigEvents();
//        scavenger = null;
        mapItems = new MapItems();
        mapItems.addMapItem(new MapItem("Spawn", Bingo4.getSpawn(), MapItem.SPAWN));
        periodicChecks.stop();
//        vaporizationChecks.reset();
        gameScoreThreshold = 0;
    }

    public void startTimeline() {
        keepRunning = true;
        mainTimelineMinutes();
        TextUtil.debugText("Started Timeline");
        periodicChecks.start();
    }

    public void pauseTimeline() {
        keepRunning = false;
        timeline.cancel();
        TextUtil.debugText("Paused Timeline");
    }

    /*Changes the world border instantly to the startingRadius and then gradually goes down to the ending Radius*/
    //Not Used currently
    public void changeWorldBorder(Location newCenter, int startingRadius, int endingRadius, int duration) {
        WorldBorder worldBorder = plugin.getWorld().getWorldBorder();
        worldBorder.setCenter(newCenter);
        worldBorder.setSize(startingRadius * 2);
        worldBorder.changeSize(endingRadius * 2, duration);
    }

    protected List<Location> generateLocationsForCamp(Structures camp, int amount) {
        return campManager.generateLocations(
                amount,
                Bingo4.getSpawn(),
                plugin.getSettingsUI().getSettings().getWorldBorderSize(),
                campManager.getCampType(camp.getId()));
    }

    public void generateLocationsForCamps() {
//TODO: REMOVE AND TURN ABSTRACT ONCE YOU CHANGE ALL TIMELINES
    };
    protected abstract void checkForMidGameEvents();
    protected void checkForCampSpawnNeeds() {
//TODO: REMOVE AND TURN ABSTRACT ONCE YOU CHANGE ALL TIMELINES

    }
    protected abstract List<Pair<String, Integer>> initNextBigEvents();
    protected int getFinalAscensionBorderShrinkSize() {
        return 500;
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
                checkForCampSpawnNeeds();
                updatePlayers();
//                vaporizationChecks.sendWarningMessage();
                if (getTimeTillNextBigEvent() == 1) {
                    mainTimelineSeconds();
                }
                if (MINUTES > 0) {
                    MINUTES--;
                } else {
                    cancel();
                    TextUtil.debugText("Timeline Ended");
//                    new FinalAscension(getFinalAscensionBorderShrinkSize());
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
                    MINUTES++;
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

    public Component getTime(PlayerData playerData) {
        if (getTimeTillNextBigEvent() > 1) {
            return TextUtil.makeText("    " + (getTimeTillNextBigEvent()) + " Minutes", TextUtil.WHITE);
        } else {
            return TextUtil.makeText("    " + SECONDS + " Seconds", TextUtil.WHITE);
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

    public Pair<String, Integer> getNextBigEvent() {
        if (nextBigEvent.isEmpty()) {
            return new MutablePair<>("Final Bingo4", STARTING_MINUTES);
        }
        return nextBigEvent.getFirst();
    }

    public Pair<String, Integer> getNextBigEvent(PlayerData playerData) {
        return getNextBigEvent();
    }

    public Pair<Component, Integer> getProgress(Team team) {
        return Pair.of(TextUtil.makeText("  Enlightenment: ", TextUtil.WHITE),
                    team.getTeamStats().getGameScorePercentage());
    }
    public int getStartingMinutes() {
        return STARTING_MINUTES;
    }
    public MapItems getMapItems() {
        return mapItems;
    }
    public int getTimePassed() {
        return STARTING_MINUTES - MINUTES;
    }
    public int getTimeTillNextBigEvent() {
        return MINUTES - (STARTING_MINUTES - getNextBigEvent().getRight());
    }

    public int getGameScoreThreshold() {
        return gameScoreThreshold;
    }

    public void setGameScoreThreshold(int gameScoreThreshold) {
        this.gameScoreThreshold = gameScoreThreshold;
        if (gameScoreThreshold > 0) {
            TextUtil.broadcastMessage(TextUtil.makeText("A new threshold has been solidified", TextUtil.GRAY, true, false));
        }
        TextUtil.debugText("New GameScore Threshold: " + gameScoreThreshold);
    }

    public BingoLockdown getBingoLockdown() {
        return bingoLockdown;
    }

    public CampManager getCampManager() {
        return campManager;
    }
}
