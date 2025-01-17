package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Settings.Presets.Custom;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.StandardTimeline;

import java.util.ArrayList;
import java.util.List;

public class BuildCustom {
    private int worldBorderSize;
    // private Timeline timeline;
    // private List<GameStartSequence> sequence;
    private List<Integer> itemDistribution; // Easy | Medium | Hard | Custom
    private int ascensionTimer;
    private int ascensionGameScoreRequirement;

    private final ScalarButtons incrementButtons;

    public BuildCustom(ScalarButtons buttons) {
        this.incrementButtons = buttons;
        itemDistribution = new ArrayList<>(List.of(0, 0, 0, 0));
    }

    public boolean parse() {
        for (ScalarButton button : incrementButtons.getKeys()) {
            button.build(this);
        }
        int amount =0;
        for (int i : itemDistribution) {
            amount += i;
        }
        if (amount != 25) {
            return false;
        }
        return true;
    }

    public Settings build() {
        return new Custom(worldBorderSize, ascensionGameScoreRequirement, new StandardTimeline(), ascensionTimer, itemDistribution);
    }

    public void setWorldBorderSize(int worldBorderSize) {
        this.worldBorderSize = worldBorderSize;
    }


    // public void setTimeline(Timeline timeline) {
    //     this.timeline = timeline;
    // }

    public void setItemDistribution(int index, int value) {
        if (index > itemDistribution.size()) {
            return;
        }
        itemDistribution.set(index, value);
    }

    public void setAscensionTimer(int ascensionTimer) {
        this.ascensionTimer = ascensionTimer;
    }

    public void setAscensionGameScoreRequirement(int ascensionGameScoreRequirement) {
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;
    }
}
