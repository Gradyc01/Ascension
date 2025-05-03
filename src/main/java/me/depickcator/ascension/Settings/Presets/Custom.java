package me.depickcator.ascension.Settings.Presets;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.Sequences.*;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Timeline.Timeline;

import java.util.ArrayList;
import java.util.List;

public class Custom extends Settings {
    public Custom(int worldBorderSize, int gameScoreReq, Timeline timeline, int ascensionTimer, List<Integer> itemDistribution, int teamSize) {
        super("Custom", worldBorderSize, gameScoreReq, timeline, ascensionTimer, itemDistribution, teamSize);
    }

    @Override
    protected List<GameStartSequence> initSequence() {
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
