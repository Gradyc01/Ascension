package org.bingo4.Timeline.Timelines;

import org.bingo4.Bingo4;
import org.bingo4.Structures.CampManager;
import org.bingo4.Structures.Structures;
import org.bingo4.Timeline.CampTimeCheck;
import org.bingo4.Timeline.Events.GracePeriod.GracePeriodEnds;
import org.bingo4.Timeline.Timeline;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bingo4.Structures.Structures.*;

public class StandardTimeline extends Timeline {

    private List<Location> bingoTurnInLocations;
    private final Map<Structures, CampTimeCheck> campTimeCheckMap;


    public StandardTimeline() {
        super(60);
        campTimeCheckMap = new HashMap<>();
    }

    @Override
    public void resetTimeline() {
        super.resetTimeline();
        campTimeCheckMap.clear();
    }

    @Override
    public void generateLocationsForCamps() {
        bingoTurnInLocations = getCampManager().generateLocations(
                20,
                Bingo4.getSpawn(),
                plugin.getSettingsUI().getSettings().getWorldBorderSize(),
                getCampManager().getCampType(BINGO_TURN_IN.getId()));

        campTimeCheckMap.put(BINGO_TURN_IN, new CampTimeCheck(1, -5, 4));
        campTimeCheckMap.put(MINING_CAMP_1, new CampTimeCheck(3, -5, 5));
        campTimeCheckMap.put(MINING_CAMP_2, new CampTimeCheck(5, -5, 3));
        campTimeCheckMap.put(MINING_CAMP_2_GEODE_VARIANT, new CampTimeCheck(7, -5, 2));
        campTimeCheckMap.put(MINING_CAMP_2_TRIAL_VARIANT, new CampTimeCheck(7, -5, 2));
        campTimeCheckMap.put(MINING_CAMP_3, new CampTimeCheck(7, -5, 2));
        campTimeCheckMap.put(MINING_CAMP_3_LUSH_VARIANT, new CampTimeCheck(7, -5, 2));
        campTimeCheckMap.put(MINING_CAMP_3_SULFUR_VARIANT, new CampTimeCheck(7, -5, 2));
    }

    @Override
    protected void checkForCampSpawnNeeds() {
        for (Map.Entry<Structures, CampTimeCheck> entry : new HashMap<>(campTimeCheckMap).entrySet()) {
            CampTimeCheck timeCheck = entry.getValue();
            if (timeCheck.minutesPassed() + 1 >= timeCheck.periodCheckMinutes()) {
                TextUtil.debugText("[Timeline Camp Refresh] Checking to see if camp " + entry.getKey().getId() + " needs additional camps");
                int numCamps = getCampManager().getCamps(entry.getKey().getId()).size();
                if (numCamps < timeCheck.objectLimit()) {
                    getCampManager().generateCamps(generateLocationsForCamp(entry.getKey(), timeCheck.objectLimit() - numCamps), getCampManager().getCampType(entry.getKey()));
                    TextUtil.debugText("[Timeline Camp Refresh] Camp " + entry.getKey().getId() + " has been refreshed with new camps");
                }
                campTimeCheckMap.put(entry.getKey(), new CampTimeCheck(timeCheck.periodCheckMinutes(), 0, timeCheck.objectLimit()));
            } else {
                campTimeCheckMap.put(entry.getKey(), new CampTimeCheck(timeCheck.periodCheckMinutes(), timeCheck.minutesPassed() + 1, timeCheck.objectLimit()));
            }
        }
    }

    @Override
    protected void checkForMidGameEvents() {
        CampManager campManager = getCampManager();
        switch (getTimePassed()) {
            case 0 -> {
                getNetherManager().setNetherInstances(campManager.generateCamps(generateLocationsForCamp(NETHER_KOTH, 2), campManager.getCampType(NETHER_KOTH)));
                setMidbossInstance(campManager.generateCamps(generateLocationsForCamp(MIDBOSS, 1, 100), campManager.getCampType(MIDBOSS)));
            }
            case 1 -> {
                new GracePeriodEnds();
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 8);
            }
            case 2 -> {
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 4);
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_1, 12), campManager.getCampType(MINING_CAMP_1));
            }
            case 3, 4 -> {
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 4);
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2, 3), campManager.getCampType(MINING_CAMP_2));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2_TRIAL_VARIANT, 1), campManager.getCampType(MINING_CAMP_2_TRIAL_VARIANT));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2_GEODE_VARIANT, 1), campManager.getCampType(MINING_CAMP_2_GEODE_VARIANT));
            }
            case 5, 6, 7 -> {
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_1, 1), campManager.getCampType(MINING_CAMP_1));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2_TRIAL_VARIANT, 1), campManager.getCampType(MINING_CAMP_2_TRIAL_VARIANT));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2_GEODE_VARIANT, 1), campManager.getCampType(MINING_CAMP_2_GEODE_VARIANT));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_3, 2), campManager.getCampType(MINING_CAMP_3));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_3_LUSH_VARIANT, 1), campManager.getCampType(MINING_CAMP_3_LUSH_VARIANT));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_3_SULFUR_VARIANT, 1), campManager.getCampType(MINING_CAMP_3_SULFUR_VARIANT));
            }
            case 15, 45 -> {
                getNetherManager().start();
            }
            case 30 -> {
                getNetherManager().start();
                getMidbossInstance().spawn();
            }
        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 1),
                new MutablePair<>("Nether Opens", 15),
                new MutablePair<>("Gatekeeper Spawns", 30),
                new MutablePair<>("Nether Opens", 45),
                new MutablePair<>("Game Ends", getStartingMinutes())
        ));
    }
}
