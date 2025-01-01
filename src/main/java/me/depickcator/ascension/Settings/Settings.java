package me.depickcator.ascension.Settings;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

public abstract class Settings {
    private final int worldBorderSize;
    private final Timeline timeline;
    private final List<GameStartSequence> sequence;
    private final List<Integer> itemDistribution; // Easy | Medium | Hard | Custom
    private final String name;
    private final int ascensionGameScoreRequirement;

    public Settings(String name, int worldBorderSize, int ascensionGameScoreRequirement, Timeline timeline) {
        this.name = name;
        this.worldBorderSize = worldBorderSize;
        this.timeline = timeline;
        this.itemDistribution = initItemDistribution();
        this.ascensionGameScoreRequirement = ascensionGameScoreRequirement;

        this.sequence = initSequence();
    }

    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(worldBorderSize),
                new SpreadPlayers(worldBorderSize),
                new InitBingoBoard(itemDistribution),
                new TextSequence(),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }

    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                5,
                10,
                5,
                5
        ));
    }


    public int getWorldBorderSize() {
        return worldBorderSize;
    };

    public Timeline getTimeline() {
        return timeline;
    }

    public List<GameStartSequence> getSequence() {
        return sequence;
    }

    public List<Integer> getItemDistribution() {
        return itemDistribution;
    }

    public String getName() {
        return name;
    }

    public int getAscensionGameScoreRequirement() {
        return ascensionGameScoreRequirement;
    }
}
