package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.Items.ItemLists.ScavengerLists.Input_Quickplay;
import me.depickcator.ascension.Items.ItemLists.ScavengerLists.Output_Quickplay;
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

public class QuickplayTimeline extends Timeline {

    public QuickplayTimeline() {
        super(60);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 1 -> {
                setScavenger(new Scavenger(300, new Input_Quickplay().getItems(5), new Output_Quickplay().getItems(5)));
                getScavenger().announceTrades();
            }
            case 15 -> {
                new GracePeriodEnds();
            }
            case 17 -> {vaporizationChecks.announceNewThreshold(15);}
            case 37, 49 -> {vaporizationChecks.announceNewThreshold(10);}
            case 11 -> {
                setAscensionEvent(new AscensionEvent(200));
            }
            case 18, 38 -> {
                getSoulShops().generateShops();
            }
            case 16, 29, 40, 50  -> {
                new CarePackage(500);
            }
            case 19, 43 -> {
                getScavenger().announceSpawnLocation();
            }
            case 24, 48 -> {
                getScavenger().spawnInScavenger();
            }
            case 35 -> {
                new Feast();

            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 15),
                new MutablePair<>("Feast", 35),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
