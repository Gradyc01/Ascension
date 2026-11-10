package org.bingo4.Settings.Presets;

import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Start.Sequences.*;
import org.bingo4.Settings.Settings;
import org.bingo4.Timeline.Timelines.StandardTimeline;

import java.util.ArrayList;
import java.util.List;

public class Testing extends Settings {
    public Testing() {
        super("Testing", 550, 1, new StandardTimeline(),120, 3, false);
    }

    @Override
    protected List<GameSequences> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new AdjustStartingGameScore(1),
                new TextSequence(1),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }

    @Override
    public boolean isReseedAfterGame() {
        return false;
    }
}
