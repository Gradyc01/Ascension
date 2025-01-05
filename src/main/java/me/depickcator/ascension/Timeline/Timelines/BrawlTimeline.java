package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.Timeline.Events.GracePeriod.GracePeriodEnds;
import me.depickcator.ascension.Timeline.Timeline;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BrawlTimeline extends Timeline {

    public BrawlTimeline() {
        super(0);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
//            case 1 -> {
//                setAscensionEvent(new AscensionEvent(150));
//            }
            case 0 -> {
                new GracePeriodEnds();
            }
//            case 5, 12, 30  -> {
//                new CarePackage(200);
//            }
//            case 20 -> {
//                new Feast();
//            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 0),
//                new MutablePair<>("Feast", 20),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }

    @Override
    protected int getFinalAscensionBorderShrinkSize() {
        return 100;
    }
}
