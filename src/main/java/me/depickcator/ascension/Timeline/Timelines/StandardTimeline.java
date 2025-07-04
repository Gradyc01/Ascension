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
        super(110);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 3 -> {
                setScavenger(new Scavenger(350));
                getScavenger().announceTrades();
            }
            case 20 -> {
                new GracePeriodEnds();
            }
            case 10 -> {
                setAscensionEvent(new AscensionEvent(500));
            }
            case 25, 50, 73, 100 -> {
                getSoulShops().generateShops();
            }
            case 30, 55, 75, 90 -> {
                new CarePackage(500);
            }
            case 40, 82 -> {
                getScavenger().announceSpawnLocation();
            }
            case 45, 87 -> {
                getScavenger().spawnInScavenger();
            }
            case 70 -> {
                new Feast();
            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 20),
                new MutablePair<>("Feast", 70),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
