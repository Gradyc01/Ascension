package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.QuickplayTimeline;

import java.util.ArrayList;
import java.util.List;

public class Quickplay extends Settings {
    public Quickplay() {
        super("Quickplay", 1000, 15, new QuickplayTimeline());
    }

    @Override
    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                8,
                9,
                2,
                6
        ));
    }

}
