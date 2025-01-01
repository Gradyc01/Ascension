package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.StandardTimeline;

public class Standard extends Settings {
    public Standard() {
        super("Standard", 3000, 25, new StandardTimeline());
    }

}
