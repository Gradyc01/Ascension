package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Start.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.QuickplayTimeline;

import java.util.ArrayList;
import java.util.List;

public class Quickplay extends Settings {
    public Quickplay() {
        super("Quickplay", 1000, 20, new QuickplayTimeline(), 720, 2, true);
    }

    @Override
    protected List<GameSequences> initSequence() {
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
