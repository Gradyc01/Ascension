package org.bingo4.Settings.Presets;

import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Start.Sequences.*;
import org.bingo4.Settings.Settings;
import org.bingo4.Timeline.Timelines.QuickplayTimeline;

import java.util.ArrayList;
import java.util.List;

public class Quickplay extends Settings {
    public Quickplay() {
        super("Quickplay", 1000, 2, new QuickplayTimeline(), 330, 2, true);
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
