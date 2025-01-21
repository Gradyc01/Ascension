package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.QuickplayTimeline;

import java.util.ArrayList;
import java.util.List;

public class Quickplay extends Settings {
    public Quickplay() {
        super("Quickplay", 1000, 20, new QuickplayTimeline(), 720);
    }

    @Override
    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new AdjustStartingGameScore(5),
                new TextSequence(),
                new ResetWorld(),
                new LaunchSequence()
        ));
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
