package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.GracePeriod.GracePeriodEnds;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import me.depickcator.ascension.Timeline.Timeline;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class StandardTimeline extends Timeline {

    public StandardTimeline() {
        super(120);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 3 -> {
                setScavenger(new Scavenger(500));
                getScavenger().announceTrades();
            }
            case 20 -> {
                new GracePeriodEnds();
            }
            case 25 -> {
                setAscensionEvent(new AscensionEvent(500));
            }
            case 35, 65, 85, 112 -> {
                new CarePackage(1000);
            }
            case 45, 95 -> {
                getScavenger().announceSpawnLocation();
            }
            case 50, 100 -> {
                getScavenger().spawnInScavenger();
            }
            case 80 -> {
                new Feast();
            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 20),
                new MutablePair<>("Feast", 80),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
