package me.depickcator.ascension.General.Game.Reset;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.General.Game.Reset.Sequences.*;
import me.depickcator.ascension.General.Game.Start.Sequences.SetWorldBorder;
import me.depickcator.ascension.Persistence.SettingsWriter;
import me.depickcator.ascension.Utility.TextUtil;

import java.util.List;

public class ResetGame extends GameLauncher {

    public ResetGame(boolean saveStats) {
        super();
        if (saveStats) {
            sequence.addFirst(new SaveGameStatistics());
        }
        start();
    }

    public ResetGame() {
        this(false);
    }
    @Override
    protected List<GameSequences> initSequence() {
        return List.of(
                new ReloadLobby(),
                new ResetTeams(),
                new ResetPlayers(),
                new SetWorldBorder(29999984),
                new LoadGameRules(),
                new ResetBackpacks()
        );
    }

    @Override
    protected boolean canStart() {
        plugin.getGameState().setCurrentState(GameStates.LOBBY_NORMAL);
        plugin.getSettingsUI().getSettings().getTimeline().resetTimeline();
        return true;
    }

    @Override
    protected void end() {
        TextUtil.debugText("Game has been Reset!");
        plugin.getGameState().setCurrentState(GameStates.LOBBY_NORMAL);
        SettingsWriter writer = new SettingsWriter();
        try {
            writer.open();
        } catch (Exception e) {
            TextUtil.debugText("Failed to open file" + e.getMessage());

        }
        writer.write();
        writer.close();
    }
}
