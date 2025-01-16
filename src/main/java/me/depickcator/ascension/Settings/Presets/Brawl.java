package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.BrawlTimeline;

import java.util.ArrayList;
import java.util.List;

public class Brawl extends Settings {
    public Brawl() {
        super("Brawl", 300, 25, new BrawlTimeline());
    }

    @Override
    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new GiveBrawlItems(),
                new AdjustStartingGameScore(10),
                new TextSequence(5),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }

    @Override
    protected List<Integer> initItemDistribution() {
        return new ArrayList<>(List.of(
                0,
                0,
                0,
                0
        ));
    }
}
