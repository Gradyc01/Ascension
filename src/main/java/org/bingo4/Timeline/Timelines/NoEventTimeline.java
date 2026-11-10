package org.bingo4.Timeline.Timelines;

import org.bingo4.Timeline.Events.GracePeriod.GracePeriodEnds;
import org.bingo4.Timeline.Timeline;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class NoEventTimeline extends Timeline {

    public NoEventTimeline() {
        super(55);
    }

    @Override
    protected void checkForMidGameEvents() {
        switch (getTimePassed()) {
            case 10 -> {
                new GracePeriodEnds();
            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 10),
                new MutablePair<>("Final Bingo4", getStartingMinutes())
        ));
    }
}
