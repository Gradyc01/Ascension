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
        super(90);
        campTimeCheckMap = new HashMap<>();
    }

    @Override
    public void generateLocationsForCamps() {
        bingoTurnInLocations = getCampManager().generateLocations(
                20,
                Bingo4.getSpawn(),
                plugin.getSettingsUI().getSettings().getWorldBorderSize(),
                getCampManager().getCampType(BINGO_TURN_IN.getId()));

        campTimeCheckMap.put(BINGO_TURN_IN, new CampTimeCheck(1, -5, 4));
        campTimeCheckMap.put(MINING_CAMP_1, new CampTimeCheck(3, -5, 12));
        campTimeCheckMap.put(MINING_CAMP_2, new CampTimeCheck(5, -5, 10));
        campTimeCheckMap.put(MINING_CAMP_3, new CampTimeCheck(7, -5, 8));
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
            case 1 -> {
                new GracePeriodEnds();
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 5);
            }
            case 2 -> {
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 5);
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_1, 10), campManager.getCampType(MINING_CAMP_1));
            }
            case 3, 4 -> {
                campManager.generateCamps(bingoTurnInLocations, campManager.getCampType(BINGO_TURN_IN.getId()), 5);
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2, 5), campManager.getCampType(MINING_CAMP_2));
            }
            case 5, 6, 7 -> {
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_1, 3), campManager.getCampType(MINING_CAMP_1));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_2, 2), campManager.getCampType(MINING_CAMP_2));
                campManager.generateCamps(generateLocationsForCamp(MINING_CAMP_3, 4), campManager.getCampType(MINING_CAMP_3));
            }
        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 1),
                new MutablePair<>("Final Bingo4", getStartingMinutes())
        ));
    }
}
