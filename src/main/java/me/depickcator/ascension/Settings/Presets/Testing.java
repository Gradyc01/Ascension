package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Start.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.StandardTimeline;

import java.util.ArrayList;
import java.util.List;

public class Testing extends Settings {
    public Testing() {
        super("Testing", 550, 15, new StandardTimeline(),900, 3, false);
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
