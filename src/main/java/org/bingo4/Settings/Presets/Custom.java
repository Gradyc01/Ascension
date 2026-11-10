package org.bingo4.Settings.Presets;

import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Start.Sequences.*;
import org.bingo4.Settings.Settings;
import org.bingo4.Timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

public class Custom extends Settings {
    public Custom(int worldBorderSize, int gameScoreReq, Timeline timeline, int ascensionTimer, List<Integer> itemDistribution, int teamSize) {
        super("Custom", worldBorderSize, gameScoreReq, timeline, ascensionTimer, itemDistribution, teamSize, false);
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

}
