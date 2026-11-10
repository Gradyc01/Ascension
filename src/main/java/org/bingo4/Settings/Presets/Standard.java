package org.bingo4.Settings.Presets;

import org.bingo4.Settings.Settings;
import org.bingo4.Timeline.Timelines.StandardTimeline;

public class Standard extends Settings {
    public Standard() {
        super("Standard", 500, 1, new StandardTimeline(), true);
    }

}
