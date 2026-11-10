package org.bingo4.Settings.Presets;

import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Start.Sequences.*;
import org.bingo4.Settings.Settings;
import org.bingo4.Timeline.Timelines.QuickplayTimeline;

import java.util.ArrayList;
import java.util.List;

public class QuickBoard extends Settings {
    public QuickBoard() {
        super("Quick Board", 1000, 1, new QuickplayTimeline(), 0, 4, true);
    }

    @Override
    protected List<GameSequences> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
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
