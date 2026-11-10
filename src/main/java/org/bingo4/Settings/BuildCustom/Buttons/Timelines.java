package org.bingo4.Settings.BuildCustom.Buttons;

import org.bingo4.Settings.BuildCustom.BuildCustom;
import org.bingo4.Settings.BuildCustom.ChoiceButton;
import org.bingo4.Timeline.Timeline;
import org.bingo4.Timeline.Timelines.NoEventTimeline;
import org.bingo4.Timeline.Timelines.QuickplayTimeline;
import org.bingo4.Timeline.Timelines.StandardTimeline;
import org.bukkit.Material;

import java.util.HashMap;

public class Timelines extends ChoiceButton {
    private final HashMap<String, Timeline> timelines;
    public Timelines() {
        super(Material.COMPASS, "Timeline", "Standard");
        timelines = new HashMap<>();
        initTimelines();
    }

    private void initTimelines() {
        addTimeline("Eventless", new NoEventTimeline(), Material.CHEST);
        addTimeline("Quickplay", new QuickplayTimeline(), Material.RECOVERY_COMPASS);
        addTimeline("Standard", new StandardTimeline(), Material.COMPASS);
    }

    private void addTimeline(String title, Timeline timeline, Material material) {
        timelines.put(title, timeline);
        addChoice(title, material);
    }

    @Override
    public boolean build(BuildCustom custom) {
        custom.setTimeline(timelines.get(getCurrentSelection()));
        return true;
    }
}
