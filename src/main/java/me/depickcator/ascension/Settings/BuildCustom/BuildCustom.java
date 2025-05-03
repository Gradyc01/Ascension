package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Settings.Presets.Custom;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

public class BuildCustom {
    private int worldBorderSize;
    private Timeline timeline;
    // private List<GameStartSequence> sequence;
    private List<Integer> itemDistribution; // Easy | Medium | Hard | Custom
    private int ascensionTimer;
    private int ascensionGameScoreRequirement;
    private int teamSize;

    private final AllButtons incrementButtons;

    public BuildCustom(AllButtons buttons) {
        this.incrementButtons = buttons;
        teamSize = 3;
        itemDistribution = new ArrayList<>(List.of(0, 0, 0, 0));
    }

    public boolean parse() {
        for (Button button : incrementButtons.getKeys()) {
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
        return new Custom(worldBorderSize, ascensionGameScoreRequirement, timeline, ascensionTimer, itemDistribution, teamSize);
    }

    public void setWorldBorderSize(int worldBorderSize) {
        this.worldBorderSize = worldBorderSize;
    }


     public void setTimeline(Timeline timeline) {
         this.timeline = timeline;
     }

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

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
}
