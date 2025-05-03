package me.depickcator.ascension.Timeline.Timelines;

import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.GracePeriod.GracePeriodEnds;
import me.depickcator.ascension.Timeline.Timeline;
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
            case 8 -> {
                setAscensionEvent(new AscensionEvent(500));
            }

        }
    }

    @Override
    protected List<Pair<String, Integer>> initNextBigEvents() {
        return new ArrayList<>(List.of(
                new MutablePair<>("Grace Period Ends", 10),
                new MutablePair<>("Final Ascension", getStartingMinutes())
        ));
    }
}
