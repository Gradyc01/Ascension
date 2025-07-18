package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.GracePeriod.GracePeriodEnds;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class StandardTimeline extends Timeline {

    public StandardTimeline() {
        super(100);
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
            case 25, 50, 63, 90 -> {
                getSoulShops().generateShops();
            }
            case 24, 41, 62, 78 -> {
                vaporizationChecks.announceNewThreshold(15);
            }
            case 30, 51, 65, 80 -> {
                new CarePackage(500);
            }
            case 40, 72 -> {
                getScavenger().announceSpawnLocation();
            }
            case 45, 77 -> {
                getScavenger().spawnInScavenger();
            }
            case 60 -> {
                new Feast();
            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 20),
                new MutablePair<>("Feast", 60),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
