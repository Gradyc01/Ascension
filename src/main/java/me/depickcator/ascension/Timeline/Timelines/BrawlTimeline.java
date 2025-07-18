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
            case 0 -> {
                new GracePeriodEnds();
            }
        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 0),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }

    @Override
    protected int getFinalAscensionBorderShrinkSize() {
        return 300;
    }
}
