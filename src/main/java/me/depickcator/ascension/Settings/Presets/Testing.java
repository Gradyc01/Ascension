package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timelines.StandardTimeline;

import java.util.ArrayList;
import java.util.List;

public class Testing extends Settings {
    public Testing() {
        super("Testing", 500, 15, new StandardTimeline());
    }

    @Override
    protected List<GameStartSequence> initSequence() {
        return new ArrayList<>(List.of(
                new ResetPlayers(),
                new SetWorldBorder(getWorldBorderSize()),
                new SpreadPlayers(getWorldBorderSize()),
                new InitBingoBoard(getItemDistribution()),
                new AdjustStartingGameScore(12),
                new TextSequence(1),
                new ResetWorld(),
                new LaunchSequence()
        ));
    }
}
