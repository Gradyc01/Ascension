package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.StandardTimeline;

public class InstantAscension extends Settings {
    public InstantAscension() {
        super("Board Only", 2500, 25, new StandardTimeline(), 1);
    }
}
